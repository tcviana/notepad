package com.tiago.notepad.infrastructure

import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redisConnectionFactory()

        // Use StringRedisSerializer for keys
        template.keySerializer = StringRedisSerializer()

        // Use GenericJackson2JsonRedisSerializer for values
        template.valueSerializer = GenericJackson2JsonRedisSerializer()

        return template
    }

    @Bean
    fun cacheManager(): CacheManager {
        return RedisCacheManager.builder(redisConnectionFactory()).build()
    }

}