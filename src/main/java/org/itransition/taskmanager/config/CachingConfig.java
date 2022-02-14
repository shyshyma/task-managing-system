package org.itransition.taskmanager.config;

import org.itransition.taskmanager.constant.CacheNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

@EnableCaching
@Configuration
public class CachingConfig {

    @Value("${spring.cache.redis.time-to-live}")
    private Duration ttlInMilliseconds;

    @Value("${spring.cache.redis.key-prefix}")
    private String cacheNamePrefix;

    @Primary
    @Bean("redisCacheConfiguration")
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith(cacheNamePrefix)
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(RedisSerializer.java()))
                .entryTtl(ttlInMilliseconds)
                .disableCachingNullValues();
    }

    @Primary
    @Bean("redisCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
                                     RedisCacheConfiguration configuration) {

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(configuration)
                .initialCacheNames(processFromCacheNamesEnum())
                .build();
    }

    private Set<String> processFromCacheNamesEnum() {
        return EnumSet.allOf(CacheNames.class).stream()
                .map(CacheNames::getName)
                .collect(Collectors.toSet());
    }
}
