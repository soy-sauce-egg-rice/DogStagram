package com.example.dogstar.controller;

import com.example.dogstar.domain.Member;
import com.example.dogstar.dto.MemberDTO;
import com.example.dogstar.repository.MemberRepository;
import com.example.dogstar.service.MemberService;
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
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {


    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper; // 직렬화 역직렬화 에 필요
    @Autowired
    private WebApplicationContext context;
    @BeforeEach
    public void mockMvcSetUp() { // 테스트 하기 전에 db 지우기
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        memberRepository.deleteAll();
    }
    @Test
    @DisplayName("회원 가입 확인")
    public void join() throws Exception {
        /* given */
        final String url = "/member";
        final String id = "testMember123";
        final String password = "testPassword";
        final String email = "testEmail";
        final MemberDTO memberDTO = MemberDTO.builder()
                .id(id)
                .password(password)
                .email(email)
                .build();

        /* when */
        // 저장
//        Member joinedMember = memberService.saveMember(memberDTO); // 회원가입
//        Member joinedMember = memberRepository.save(memberDTO.toEntity());

//         json 으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(memberDTO);

        /* when */
        // 요청
        ResultActions result = mockMvc.perform( // 회원가입 2
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody));

//        Member member = memberService.findMember(joinedMember).get();
//        Member member = memberRepository.findById(joinedMember.getId()).get();

        /* then */
        result.andExpect(MockMvcResultMatchers.status().is(200));
//        Assertions.assertThat(member).isEqualTo(joinedMember);
    }
}
