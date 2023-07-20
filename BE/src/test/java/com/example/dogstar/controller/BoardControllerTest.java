package com.example.dogstar.controller;

import com.example.dogstar.domain.Board;
import com.example.dogstar.dto.BoardDTO;
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

//    @BeforeEach
//    public void mockMvcSetUp() { // 테스트 하기 전에 db 지우기
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .build();
//        boardRepository.deleteAll();
//    }

    @DisplayName("addBoard : 게시글 추가")
    @Test
    public void addBoard() throws Exception{
        /* given */
        final String url = "/board";
        final String memberId = "member1";
        final String image = "image1";
        final String content = "content1";
        final BoardDTO boardDto = new BoardDTO(memberId, image, content);

        // dto -> entity -> json 으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(boardDto.toEntity());

        /* when */
        // 요청
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody));

        /* then */
        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.memberId").value(memberId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(image))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(content));

        // BeforeEach랑 같이 사용할 때
//        List<Board> boards = boardRepository.findAll();
//        Assertions.assertThat(boards.size()).isEqualTo(1);
//        Assertions.assertThat(boards.get(0).getImage()).isEqualTo(img);
//        Assertions.assertThat(boards.get(0).getContent()).isEqualTo(content);


    }

    @Test 
    @DisplayName("findAllBoards : 모든 게시글 조회")
    void findAllBoards() throws Exception {
        final String url = "/board";

        /* when */
        // 요청
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        /* then */
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }
}