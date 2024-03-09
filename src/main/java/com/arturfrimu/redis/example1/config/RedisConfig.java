package com.arturfrimu.redis.example1.config;

import com.arturfrimu.redis.example1.entity.Article;
import com.arturfrimu.redis.example1.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port:6379}")
    private int redisPort;
    @Value("${spring.data.redis.maximum-active-connections-to-redis-instance:30}")
    private int maximumActiveConnectionsToRedisInstance;
    @Value("${spring.data.redis.number-of-connections-to-keep-in-idle-state:10}")
    private int numberOfConnectionsToKeepInIdleState;
    @Value("${spring.data.redis.minimum-number-of-idle-connections-in-the-pool:2}")
    private int minimumNumberOfIdleConnectionsInThePool = 2;
    @Value("${spring.data.redis.test-the-connection-before-borrowing-from-the-pool:true}")
    private boolean testTheConnectionBeforeBorrowingFromThePool = true;
    @Value("${spring.data.redis.test-the-connection-before-returning-to-the-pool:true}")
    private boolean testTheConnectionBeforeReturningToThePool = true;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        var redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);

        var poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maximumActiveConnectionsToRedisInstance);
        poolConfig.setMaxIdle(numberOfConnectionsToKeepInIdleState);
        poolConfig.setMinIdle(minimumNumberOfIdleConnectionsInThePool);
        poolConfig.setTestOnBorrow(testTheConnectionBeforeBorrowingFromThePool);
        poolConfig.setTestOnReturn(testTheConnectionBeforeReturningToThePool);

        var jedisClientConfiguration = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(poolConfig)
                .build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    public RedisTemplate<String, Article> redisTemplate() {
        RedisTemplate<String, Article> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        // Key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        // Value serializer
        Jackson2JsonRedisSerializer<Article> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Article.class);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, User> userRedisTemplate() {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        // Use StringRedisSerializer for the keys
        template.setKeySerializer(new StringRedisSerializer());
        // Use Jackson2JsonRedisSerializer or GenericJackson2JsonRedisSerializer for the values
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // Also set the serializer for hash keys and values, if you plan to use hash operations
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
