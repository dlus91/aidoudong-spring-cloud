package com.aidoudong.common.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching(mode = AdviceMode.PROXY)
@EnableConfigurationProperties(CacheProperties.class)
public class CacheManagerConfiguration {
	private Logger logger=LoggerFactory.getLogger(getClass());

	private final CacheProperties cacheProperties;
	
	public CacheManagerConfiguration(CacheProperties cacheProperties) {
		this.cacheProperties = cacheProperties;
	}
	
	public interface CacheManagerNames {
		String REDIS_CACHE_MANAGER = "redisCacheManager";
		String EHCACHE_CACHE_MANAGER = "ehCacheManager";
		String CACHE_MANAGER = "cacheManager";
	}
	
	// 配置key生成器，作用于缓存管理器管理的所有缓存
	// 如果缓存注解（@Cacheable、@CacheEvict等）中指定key属性，那么会覆盖此key生成器
	@Bean
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(method.getName());
			for (Object obj : params) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}

	@Bean(name = CacheManagerNames.REDIS_CACHE_MANAGER)	
	public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration config = RedisConstants.getRedisCacheConfiguration();
		//缓存配置map
		Map<String,RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
		//自定义缓存名，后面使用的@Cacheable的CacheName
		cacheConfigurationMap.put(RedisConstants.cacheName,config);
		//根据redis缓存配置和reid连接工厂生成redis缓存管理器
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
			.cacheDefaults(config)
			.transactionAware()
			.withInitialCacheConfigurations(cacheConfigurationMap)
			.build();
		logger.debug("自定义RedisCacheManager加载完成");
		return redisCacheManager;
	}

	@Bean(name = CacheManagerNames.EHCACHE_CACHE_MANAGER)
	public EhCacheCacheManager ehCacheManager() {
		Resource resource = this.cacheProperties.getEhcache().getConfig();
		resource = this.cacheProperties.resolveConfigLocation(resource);
		EhCacheCacheManager ehCacheManager = new EhCacheCacheManager(
			EhCacheManagerUtils.buildCacheManager(resource)
		);
		ehCacheManager.afterPropertiesSet();
		return ehCacheManager;
	}

	@Bean(name = CacheManagerNames.CACHE_MANAGER)
	@Primary
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheManager cacheManager = LocalRedisCacheManager.create(redisConnectionFactory,
				ehCacheManager().getCache(RedisConstants.cacheName),
				this.redisTemplate(redisConnectionFactory),
				RedisConstants.cacheName);
		return cacheManager;
	}
	
	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
			MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic(RedisConstants.cacheName));
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(LocalRedisCacheManager cacheManager, RedisTemplate<String, Object> redisTemplate) {
		return new MessageListenerAdapter(new MessageListener() {
			@Override
			public void onMessage(Message message, byte[] pattern) {
				byte[] channel = message.getChannel();
				byte[] body = message.getBody();
				String cacheName = (String) redisTemplate.getStringSerializer().deserialize(channel);
				System.out.println("=====MessageListenerAdapter:"+cacheName);
				LocalRedisCache cache = (LocalRedisCache) cacheManager.getCache(cacheName);
				if(cache == null) return;
				CacheMessage cacheMessage = (CacheMessage) redisTemplate.getValueSerializer().deserialize(body);
				System.out.println("=====LocalRedisCache:"+cacheMessage.getValue());
				cache.sub(cacheMessage);
			}
		});
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
	
}
