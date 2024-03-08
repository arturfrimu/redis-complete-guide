package com.arturfrimu.redis.example1.dao;

import com.arturfrimu.redis.example1.entity.Article;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleDaoRestTemplateAdapterV1 {

    private static final String KEY = "ARTICLE";

    private final RedisTemplate<String, Article> redisTemplate;
    private HashOperations<String, String, Article> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public Article save(Article article) {
        hashOperations.put(KEY, article.getId(), article);
        return article;
    }

    public Article findById(String id) {
        return Optional.ofNullable(hashOperations.get(KEY, id))
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    public void deleteById(String id) {
        hashOperations.delete(KEY, id);
    }

    public Iterable<Article> findAll() {
        return hashOperations.values(KEY);
    }
}
