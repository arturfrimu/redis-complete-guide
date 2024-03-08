package com.arturfrimu.redis.example1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@RedisHash("ARTICLE")
@NoArgsConstructor
@Setter
@Getter
@Table(name = "ARTICLE")
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String text;
    private BigDecimal rating;

    public Article(Long id, String title, String text, BigDecimal rating) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.rating = rating;
    }
}
