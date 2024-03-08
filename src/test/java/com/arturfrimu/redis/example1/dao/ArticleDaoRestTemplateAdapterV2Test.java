package com.arturfrimu.redis.example1.dao;

import com.arturfrimu.redis.example1.entity.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ArticleDaoRestTemplateAdapterV2Test {

    @Autowired
    private ArticleDaoRestTemplateAdapterV2 redisRepository;

    @BeforeEach
    public void clearRedis() {
        redisRepository.deleteById("1");
        redisRepository.deleteById("2");
        redisRepository.deleteById("3");
    }

    @Test
    void testArticleDao() {
        List<Article> articles = List.of(
                new Article("1", "title 1", "content 1", BigDecimal.valueOf(10)),
                new Article("2", "title 2", "content 2", BigDecimal.valueOf(20)),
                new Article("3", "title 3", "content 3", BigDecimal.valueOf(30))
        );

        assertThat(redisRepository.save(articles.get(0))).isEqualTo(articles.get(0));
        assertThat(redisRepository.save(articles.get(1))).isEqualTo(articles.get(1));
        assertThat(redisRepository.save(articles.get(2))).isEqualTo(articles.get(2));

        assertThat(redisRepository.findAll()).containsAll(articles);

        assertThat(redisRepository.findById("1")).isEqualTo(articles.get(0));
        assertThat(redisRepository.findById("2")).isEqualTo(articles.get(1));
        assertThat(redisRepository.findById("3")).isEqualTo(articles.get(2));

        redisRepository.deleteById("1");
        redisRepository.deleteById("2");
        redisRepository.deleteById("3");

        assertThat(redisRepository.findAll()).isEmpty();
    }
}