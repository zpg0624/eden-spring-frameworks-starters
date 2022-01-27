package com.edenspring.framework.redis;

import com.edenspring.framework.redis.support.RedisNumericIdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alturkovic.lock.redis.configuration.EnableRedisDistributedLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@AutoConfigureBefore(name = {"org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration",
        "org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration"})
@EnableRedisDistributedLock
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
@Configuration
public class RedisAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public RedisNumericIdGenerator defaultIdGenerator(RedisOps redisOps, @Value("${spring.application.name}") String key) {
        return new RedisNumericIdGenerator(redisOps, key);
    }

    @Bean
    public RedisOps redisOps(
            StringRedisTemplate template,
            ObjectMapper mapper,
            RedisProperties properties) {
        return new RedisOps(template, mapper, properties.getKeyPrefix());
    }

    @Bean
    public LockRegistry lockRegistry(
            RedisConnectionFactory connectionFactory,
            RedisProperties properties) {
        return new RedisLockRegistry(connectionFactory, String.format("%s:lock", properties.getKeyPrefix()), properties.getLock().getExpireAfter());
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory,
                                     RedisProperties properties) {
        return RedisCacheManager.builder(factory)
                .transactionAware()
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .prefixCacheNameWith(String.format("%s:", properties.getKeyPrefix()))
                        .serializeKeysWith(fromSerializer(RedisSerializer.string()))
                        .serializeValuesWith(fromSerializer(RedisSerializer.json())))
                .build();
    }
}
