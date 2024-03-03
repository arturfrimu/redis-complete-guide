package com.arturfrimu.redis.example1.service;

import com.arturfrimu.redis.example1.entity.Article;
import com.arturfrimu.redis.example1.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: %d".formatted(id)));
    }
}
