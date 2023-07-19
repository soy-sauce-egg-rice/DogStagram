package com.example.dogstar.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Getter
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

//    private String member_id; 
    // 나중에 연관관계 매핑

    @Column(name = "image") // 이미지 경로를 지정해야 하는지?
    private String img;

    @Column(name = "content", nullable = false)
    private String content;

//    private String originalName;
//    private String filePath;          // 첨부 파일
//    private Long fileSize;    // 첨부 이미지

//    @Builder
//    public Board(String originalName, String content, String filePath, Long fileSize) {
//        this.originalName = originalName;
//        this.content = content;
//        this.filePath = filePath;
//        this.fileSize = fileSize;
//    }

    @Builder
    public Board(String img, String content) {
        this.img = img;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }


}