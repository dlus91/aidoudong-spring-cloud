package com.aidoudong.common.cache;

import java.util.Map;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

public class LocalRedisCacheManager extends RedisCacheManager {

	private final RedisConnectionFactory connectionFactory;
    private final Cache localCache;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String topicName;

    public LocalRedisCacheManager(RedisCacheWriter cacheWriter,
			RedisCacheConfiguration defaultCacheConfiguration,
			RedisConnectionFactory connectionFactory,
			Cache localCache,
			RedisTemplate<String, Object> redisTemplate,
			String topicName) {
        super(cacheWriter, defaultCacheConfiguration);
        this.connectionFactory = connectionFactory;
        this.localCache = localCache;
        this.redisTemplate = redisTemplate;
        this.topicName = topicName;
        check();
    }
    
    public LocalRedisCacheManager(RedisCacheWriter cacheWriter,
    		RedisCacheConfiguration defaultCacheConfiguration,
    		RedisConnectionFactory connectionFactory,
    		Cache localCache,
    		RedisTemplate<String, Object> redisTemplate,
    		String topicName,
    		String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        this.connectionFactory = connectionFactory;
        this.localCache = localCache;
        this.redisTemplate = redisTemplate;
        this.topicName = topicName;
        check();
    }
    
    public LocalRedisCacheManager(RedisCacheWriter cacheWriter,
    		RedisCacheConfiguration defaultCacheConfiguration,
    		Map<String, RedisCacheConfiguration> initialCacheConfigurations,
    		RedisConnectionFactory connectionFactory,
    		Cache localCache,
    		RedisTemplate<String, Object> redisTemplate,
    		String topicName) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.connectionFactory = connectionFactory;
        this.localCache = localCache;
        this.redisTemplate = redisTemplate;
        this.topicName = topicName;
        check();
    }
    
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        return new LocalRedisCache(name, RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory),
                cacheConfig != null ? cacheConfig : RedisConstants.getRedisCacheConfiguration(),
                localCache, redisTemplate, topicName);
    }
    
    public static LocalRedisCacheManager create(RedisConnectionFactory connectionFactory, Cache localCache, RedisTemplate<String, Object> redisTemplate, String topicName) {
        Assert.notNull(localCache, "localCache must not be null");
        Assert.notNull(connectionFactory, "connectionFactory must not be null");
        Assert.notNull(redisTemplate, "redisOperations must not be null");
        Assert.notNull(topicName, "topicName must not be null");
        return new LocalRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory),
        		RedisConstants.getRedisCacheConfiguration(), connectionFactory, localCache, redisTemplate, topicName);
    }
    
    private void check() {
        Assert.notNull(localCache, "localCache must not be null");
        Assert.notNull(connectionFactory, "connectionFactory must not be null");
        Assert.notNull(redisTemplate, "redisOperations must not be null");
        Assert.notNull(topicName, "topicName must not be null");
    }
    
}
