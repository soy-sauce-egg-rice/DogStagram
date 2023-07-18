package com.example.dogstar.dto;

import com.example.dogstar.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardDto {

    private String img;
    private String content;

    public Board toEntity() {
        return Board.builder()
                .img(img)
                .content(content)
                .build();
    }
}
