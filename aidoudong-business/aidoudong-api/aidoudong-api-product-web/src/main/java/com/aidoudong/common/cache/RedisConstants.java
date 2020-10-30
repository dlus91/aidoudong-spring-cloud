package com.aidoudong.common.cache;

import java.time.Duration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisConstants {
	
	public final static String cacheName = "products";
	
	public final static Duration redisExpireDuration = Duration.ofMinutes(10);
	
	
	public static RedisCacheConfiguration getRedisCacheConfiguration() {
	    return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(redisExpireDuration)
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
				.disableCachingNullValues();
	}
	
	//redis键序列化使用StrngRedisSerializer
	private static RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	//redis值序列化使用json序列化器
	private static RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}
	
	
	
}
