package com.example.dogstar.dto;

import com.example.dogstar.domain.Board;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardDTO {

    @Setter
    private Long memberId;
    private String image;
    private String content;


    public Board toEntity() {
        return Board.builder()
                .memberId(memberId)
                .image(image)
                .content(content)
                .build();
    }
}
