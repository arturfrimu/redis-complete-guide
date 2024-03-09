package com.arturfrimu.redis.example1.service;

import com.arturfrimu.redis.example1.entity.User;
import com.arturfrimu.redis.example1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, User> userRedisTemplate;

    public User getCachedWithRedisUserByUsername(String username) {
        String key = "user:" + username;
        ValueOperations<String, User> userValueOperations = userRedisTemplate.opsForValue();
        long startCache = System.currentTimeMillis();
        User valueFromCache = userValueOperations.get(key);
        long endCache = System.currentTimeMillis();
        Optional<User> userFromCache = Optional.ofNullable(valueFromCache);
        if (userFromCache.isPresent()) {
            log.info("User from cache time: " + (endCache - startCache) + "ms");
        }
        return userFromCache
                .orElseGet(() -> {
                    long startDb = System.currentTimeMillis();
                    User dbUser = userRepository.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("User not found in database with username: " + username));
                    long endDb = System.currentTimeMillis();
                    log.info("\n\nUser from database time: " + (endDb - startDb) + "ms\n\n");
                    userValueOperations.set(key, dbUser, Duration.ofSeconds(5));
                    return dbUser;
                });
    }

    public User getUserByUsernameUsingHibernateCache(String username) {
        long startDb = System.currentTimeMillis();
        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found in database with username: " + username));
        long endDb = System.currentTimeMillis();
        log.info("User from database time: " + (endDb - startDb) + "ms");
        return dbUser;
    }
}
