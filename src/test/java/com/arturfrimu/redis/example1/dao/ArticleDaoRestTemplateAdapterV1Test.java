package com.arturfrimu.redis.example1.dao;

import com.arturfrimu.redis.example1.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ArticleDaoRestTemplateAdapterV1Test {

    @Autowired
    private ArticleDaoRestTemplateAdapterV1 articleDaoRestTemplateAdapterV1;

    @Test
    void testArticleDao() {
        List<Article> articles = List.of(
                new Article("1", "title 1", "content 1", BigDecimal.valueOf(10)),
                new Article("2", "title 2", "content 2", BigDecimal.valueOf(20)),
                new Article("3", "title 3", "content 3", BigDecimal.valueOf(30))
        );

        assertThat(articleDaoRestTemplateAdapterV1.save(articles.get(0))).isEqualTo(articles.get(0));
        assertThat(articleDaoRestTemplateAdapterV1.save(articles.get(1))).isEqualTo(articles.get(1));
        assertThat(articleDaoRestTemplateAdapterV1.save(articles.get(2))).isEqualTo(articles.get(2));

        assertThat(articleDaoRestTemplateAdapterV1.findAll()).containsAll(articles);

        assertThat(articleDaoRestTemplateAdapterV1.findById("1")).isEqualTo(articles.get(0));
        assertThat(articleDaoRestTemplateAdapterV1.findById("2")).isEqualTo(articles.get(1));
        assertThat(articleDaoRestTemplateAdapterV1.findById("3")).isEqualTo(articles.get(2));

        articleDaoRestTemplateAdapterV1.deleteById("1");
        articleDaoRestTemplateAdapterV1.deleteById("2");
        articleDaoRestTemplateAdapterV1.deleteById("3");

        assertThat(articleDaoRestTemplateAdapterV1.findAll()).isEmpty();
    }
}