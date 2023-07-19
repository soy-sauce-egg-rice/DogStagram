package com.example.dogstar.controller;

import com.example.dogstar.domain.Member;
import com.example.dogstar.dto.MemberDto;
import com.example.dogstar.repository.MemberRepository;
import com.example.dogstar.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member join(@RequestBody MemberDto memberDto) {
//        예외 처리
        if (memberDto == null || memberDto.getPassword() == null){
            throw new RuntimeException("Invalid Password value...");
        }
        // member 객체
        Member member = Member.builder()
                .id(memberDto.getId())
                .password(memberDto.getPassword())
                .email(memberDto.getEmail())
                .role("user")
                .build();
        // todo return ResponseDTO 로 변경할 것
        return memberService.saveMember(member);
    }


}
