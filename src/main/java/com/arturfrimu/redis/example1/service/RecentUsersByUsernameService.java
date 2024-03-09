package com.arturfrimu.redis.example1.service;

import com.arturfrimu.redis.example1.entity.User;
import com.arturfrimu.redis.example1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class RecentUsersByUsernameService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, User> userRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public User getUserByUsername(String username) {
        String key = "user:" + username;
        ValueOperations<String, User> userValueOperations = userRedisTemplate.opsForValue();
        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found in database with username: " + username));
        userValueOperations.set(key, dbUser, Duration.ofMinutes(10)); // Cache with a longer expiration time
        updateRecentUsers(username); // Update the list of recent usernames
        return dbUser;
    }

    private void updateRecentUsers(String username) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String recentUsersKey = "recent:users";
        listOps.leftPush(recentUsersKey, username); // Push the username to the start of the list
        listOps.trim(recentUsersKey, 0, 9); // Keep only the latest 10 usernames
    }
}
