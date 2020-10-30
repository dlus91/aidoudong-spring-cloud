package com.aidoudong.common.cache;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

public class LocalRedisCache extends RedisCache {

	private final Cache localCache;//本地一级缓存
	private final RedisTemplate<String, Object> redisTemplate;//配合topicName，发布缓存更新消息
	private final String topicName;//redis topic ，发布缓存更新消息通知其他client更新缓存

	/**
	 * Create new {@link RedisCache}. 
	 * 
	 * @param name            must not be {@literal null}. 
	 * @param cacheWriter     must not be {@literal null}. 
	 * @param cacheConfig     must not be {@literal null}. 
	 * @param localCache      must not be {@literal null}. 
	 * @param redisOperations must not be {@literal null}. 
	 * @param topicName       must not be {@literal null}. 
	 */
	protected LocalRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig,
			Cache localCache, RedisTemplate<String, Object> redisTemplate, String topicName) {
		super(name, cacheWriter, cacheConfig);
		Assert.notNull(localCache, "localCache must not be null!");
		Assert.notNull(redisTemplate, "redisOperations must not be null!");
		Assert.hasText(topicName, "topicName must not be empty!");
		this.localCache = localCache;
		this.redisTemplate = redisTemplate;
		this.topicName = topicName;
	}

	@Override
	public synchronized <T> T get(Object key, Callable<T> valueLoader) {
		//先读取本地一级缓存
		T value = localCache.get(key, valueLoader);
		if (value == null) {
			//本地一级缓存不存在，读取redis二级缓存
			value = super.get(key, valueLoader);
			if (value != null) {
				//redis二级缓存存在，存入本地一级缓存
				localCache.put(key, value);
				//发布缓存更新消息通知其他client更新缓存
				pub(new CacheMessage(key, value, CacheMessage.Type.PUT));
			}
		}
		return value;
	}

	@Override
	public void put(Object key, Object value) {
		super.put(key, value);
		localCache.put(key, value);
		pub(new CacheMessage(key, value, CacheMessage.Type.PUT));
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		ValueWrapper vw1 = localCache.putIfAbsent(key, value);
		ValueWrapper vw2 = super.putIfAbsent(key, value);
		pub(new CacheMessage(key, value, CacheMessage.Type.PUTIFABSENT));
		return vw1 == null ? vw2 : vw1;
	}

	@Override
	public void evict(Object key) {
		localCache.evict(key);
		super.evict(key);
		pub(new CacheMessage(key, CacheMessage.Type.REMOVE));
	}

	@Override
	public void clear() {
		localCache.clear();
		super.clear();
		pub(new CacheMessage(CacheMessage.Type.CLEAN));
	}

	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper valueWrapper = localCache.get(key);
		if (valueWrapper == null) {
			valueWrapper = super.get(key);
			if (valueWrapper != null) {
				localCache.put(key, valueWrapper.get());
				pub(new CacheMessage(key, valueWrapper.get(), CacheMessage.Type.PUT));
			}
		}
		return valueWrapper;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		T value = localCache.get(key, type);
		if (value == null) {
			value = super.get(key, type);
			if (value != null) {
				localCache.put(key, value);
				pub(new CacheMessage(key, value, CacheMessage.Type.PUT));
			}
		}
		return value;
	}

	/** 
	 * 更新缓存
	 *
	 * @param updateMessage
	 */
	public void sub(final CacheMessage cacheMessage) {
		if (cacheMessage.getType() == CacheMessage.Type.CLEAN) {
			//清除所有缓存
			localCache.clear();
			super.clear();
		} else if (cacheMessage.getType() == CacheMessage.Type.PUT) {
			//更新缓存
			localCache.put(cacheMessage.getKey(), cacheMessage.getValue());
			super.put(cacheMessage.getKey(), cacheMessage.getValue());
		} else if (cacheMessage.getType() == CacheMessage.Type.PUTIFABSENT) {
			//更新缓存
			localCache.putIfAbsent(cacheMessage.getKey(), cacheMessage.getValue());
			super.putIfAbsent(cacheMessage.getKey(), cacheMessage.getValue());
		} else if (cacheMessage.getType() == CacheMessage.Type.REMOVE) {
			//删除缓存
			localCache.evict(cacheMessage.getKey());
			super.evict(cacheMessage.getKey());
		}
	}

	/**
	 * 通知其他 redis clent 更新缓存
	 * 
	 * @param message
	 */
	private void pub(final CacheMessage message) {
		this.redisTemplate.convertAndSend(topicName, message);
	}

}
