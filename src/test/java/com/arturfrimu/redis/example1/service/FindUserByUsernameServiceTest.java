package com.arturfrimu.redis.example1.service;

import com.arturfrimu.redis.example1.entity.User;
import com.arturfrimu.redis.example1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class FindUserByUsernameServiceTest {

    @Autowired
    private FindUserByUsernameService findUserByUsernameService;
    @Autowired
    private UserRepository userRepository;

    /**
     * This example ilustrate how use the redis cache WRONG !!!
     * Because the totals show us that hibernate cash is faster in this case, and redis is inutil
     */
    @Test
    void testGetUserByUsername() {
        User admin = userRepository.save(new User(1L, "admin"));
        User user = userRepository.save(new User(2L, "user"));

        long startWithRedisCache = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            assertThat(findUserByUsernameService.getCachedWithRedisUserByUsername("admin")).isEqualTo(admin);
        }
        long endWithRedisCache = System.currentTimeMillis();

        long startWithHibernateCache = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            assertThat(findUserByUsernameService.getUserByUsernameUsingHibernateCache("user")).isEqualTo(user);
        }
        long endWithHibernateCache = System.currentTimeMillis();

        log.info("\n\nTOTALS\n");
        log.info("Total with redis cache time: " + (endWithRedisCache - startWithRedisCache) + "ms");
        log.info("Total with hibernate cache time: " + (endWithHibernateCache - startWithHibernateCache) + "ms");
        log.info("\n\n");
    }
}