package com.example.dogstar.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
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

    @Setter
    private String memberId;
//     나중에 연관관계 매핑

    @Setter
    @Column(name = "image") // 이미지 경로를 지정해야 하는지?
    private String image;

    @Setter
    @Column(name = "content", nullable = false)
    private String content;

    @Setter
    @Column(name = "createDate", nullable = false)
    private LocalDateTime createDate;

    @Setter
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;


    @Builder
    public Board(String image, String content ,String memberId) {
        this.memberId = memberId;
        this.image = image;
        this.content = content;
        this.createDate = LocalDateTime.now();
        this.updateDate = this.createDate;
    }

    public void update(String image, String content) {
        this.image = image;
        this.content = content;
        this.updateDate = LocalDateTime.now();
    }


}