package com.example.dogstar.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "image") // 이미지 경로를 지정해야 하는지?
    private String img;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Board(String img, String content) {
        this.img = img;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}