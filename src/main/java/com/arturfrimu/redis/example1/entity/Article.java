package com.arturfrimu.redis.example1.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("ARTICLE")
public class Article implements Serializable {
    @Id
    private String id;
    private String title;
    private String text;
    private BigDecimal rating;

    public Article(String id, String title, String text, BigDecimal rating) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(title, article.title) && Objects.equals(text, article.text) && Objects.equals(rating, article.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, rating);
    }
}
