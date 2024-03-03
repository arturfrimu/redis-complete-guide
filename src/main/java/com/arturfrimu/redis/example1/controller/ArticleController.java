package com.arturfrimu.redis.example1.controller;

import com.arturfrimu.redis.example1.entity.Article;
import com.arturfrimu.redis.example1.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<Article> findById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }
}
