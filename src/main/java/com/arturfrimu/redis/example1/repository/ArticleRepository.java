package com.arturfrimu.redis.example1.repository;

import com.arturfrimu.redis.example1.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
