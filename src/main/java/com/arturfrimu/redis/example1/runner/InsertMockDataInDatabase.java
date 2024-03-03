package com.arturfrimu.redis.example1.runner;

import com.arturfrimu.redis.example1.entity.Article;
import com.arturfrimu.redis.example1.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InsertMockDataInDatabase implements CommandLineRunner {

    private final ArticleRepository articleRepository;

    @Override
    public void run(String... args) {
        var mockArticles = List.of(
                new Article(1L, "title 1", "text 1", BigDecimal.valueOf(1.0)),
                new Article(2L, "title 2", "text 2", BigDecimal.valueOf(2.0)),
                new Article(3L, "title 3", "text 3", BigDecimal.valueOf(3.0))
        );
        articleRepository.saveAll(mockArticles);
    }
}
