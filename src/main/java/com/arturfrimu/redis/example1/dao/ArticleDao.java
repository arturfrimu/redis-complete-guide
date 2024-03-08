package com.arturfrimu.redis.example1.dao;

import com.arturfrimu.redis.example1.entity.Article;
import com.arturfrimu.redis.example1.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleDao {

    private final ArticleRepository articleRepository;

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article findById(String id) {
        return articleRepository.findById(id)
                .orElseThrow();
    }

    public void deleteById(String id) {
        articleRepository.deleteById(id);
    }

    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }
}
