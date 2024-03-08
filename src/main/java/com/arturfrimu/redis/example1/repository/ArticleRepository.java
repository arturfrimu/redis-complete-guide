package com.arturfrimu.redis.example1.repository;

import com.arturfrimu.redis.example1.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, String> {
}