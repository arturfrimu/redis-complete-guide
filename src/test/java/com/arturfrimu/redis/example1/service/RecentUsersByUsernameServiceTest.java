package com.arturfrimu.redis.example1.service;

import com.arturfrimu.redis.example1.entity.User;
import com.arturfrimu.redis.example1.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class RecentUsersByUsernameServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecentUsersByUsernameService recentUsersByUsernameService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private ListOperations<String, String> listOps;

    @PostConstruct
    void setOptions() {
        listOps = redisTemplate.opsForList();
    }

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    void testRecentUsers() {
        List<User> users = new ArrayList<>();
        log.info("\n\nSave random 100 users in database\n\n");
        for (long i = 1; i <= 100; i++) {
            users.add(userRepository.save(new User(i, "user" + i)));
        }

        log.info("\n\nRetrieve 30 users from database\n\n");
        for (int i = 0; i < 30; i++) {
            recentUsersByUsernameService.getUserByUsername(users.get(i).getUsername());
        }

        log.info("\n\nThe 10 most recent users retrieved from database\n\n");
        String recentUsersKey = "recent:users";

        List<String> range = listOps.range(recentUsersKey, 0, -1);
        assertThat(range).isNotEmpty().hasSize(10);
        range.forEach(log::info);
    }
}