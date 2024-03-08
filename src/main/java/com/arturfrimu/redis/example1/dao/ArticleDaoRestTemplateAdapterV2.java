package com.arturfrimu.redis.example1.dao;

import com.arturfrimu.redis.example1.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleDaoRestTemplateAdapterV2 {

    private static final String KEY = "ARTICLE";

    private final RedisTemplate<String, Article> redisTemplate;

    public Article save(Article article) {
        redisTemplate.opsForHash().put(KEY, article.getId(), article);
        return article;
    }

    public Article findById(String id) {
        return Optional.ofNullable((Article) redisTemplate.opsForHash().get(KEY, id))
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    public void deleteById(String id) {
        redisTemplate.opsForHash().delete(KEY, id);
    }

    public Iterable<Article> findAll() {
        return redisTemplate.opsForHash()
                .values(KEY)
                .stream()
                .map(Article.class::cast)
                .toList();
    }
}
