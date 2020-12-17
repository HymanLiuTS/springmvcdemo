package springmvcdemo.cache.configuration;

import java.util.HashSet;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching // 启用缓存
public class CachingConfig {

	/**
	 * 声明缓存管理器
	 */
	@Bean("concurrentMapCacheManager")
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("defaultCache");
	}

	@SuppressWarnings("unchecked")
	//@Bean
	public CacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
		// return new RedisCacheManager(redisTemplate);
		Set set=new HashSet(){};
		set.add("defaultCache");
		CacheManager cacheManager = RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory).initialCacheNames(set).build();
		return cacheManager;
	}

}