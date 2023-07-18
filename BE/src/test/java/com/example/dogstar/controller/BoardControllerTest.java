package com.example.dogstar.controller;

import com.example.dogstar.domain.Board;
import com.example.dogstar.dto.BoardDto;
import com.example.dogstar.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc // mockmvc 생성
class BoardControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper; // 직렬화 역직렬화 에 필요

    @Autowired
    private WebApplicationContext context;
    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void mockMvcSetUp() { // 테스트 하기 전에 db 지우기
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        boardRepository.deleteAll();
    }

    @DisplayName("addBoard : 게시글 추가")
    @Test
    public void addBoard() throws Exception{
        /* given */
        final String url = "/board";
        final String img = "image1";
        final String content = "content1";
        final BoardDto boardDto = new BoardDto(img,content);

        // json 으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(boardDto);

        /* when */
        // 요청
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody));

        /* then */
        result.andExpect(MockMvcResultMatchers.status().isCreated());

        List<Board> boards = boardRepository.findAll();

        Assertions.assertThat(boards.size()).isEqualTo(1);
        Assertions.assertThat(boards.get(0).getImg()).isEqualTo(img);
        Assertions.assertThat(boards.get(0).getContent()).isEqualTo(content);


    }

}