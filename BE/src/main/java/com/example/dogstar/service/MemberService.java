package com.example.dogstar.service;

import com.example.dogstar.domain.Member;
import com.example.dogstar.dto.MemberDTO;
import com.example.dogstar.repository.MemberRepository;
import com.example.dogstar.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService {

//    @Autowired
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    public Long saveMember(MemberDTO memberDTO) {
        // member 객체
        Member member =Member.builder()
                .email(memberDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(memberDTO.getPassword()))
                .build();

        if (memberRepository.existsByEmail(member.getEmail())) {
            log.warn("Email already exists {}", member.getEmail());
            throw new RuntimeException("Email already exists");
        }
        return memberRepository.save(member).getId();
    }

    public Member findMember (Member member) {
        return memberRepository.findByEmail(member.getEmail()).get();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public String login(MemberDTO memberDTO) {
        Member loginedMember = memberRepository.findByEmail(memberDTO.getEmail()).get();

        // 비밀번호 검사
        if (bCryptPasswordEncoder.matches(memberDTO.getPassword(), loginedMember.getPassword())){
            // refresh token 검증
//            if (refreshTokenRepository.findByUserId(loginedMember.getId()).get()
//            )
            return loginedMember.getEmail();
        } else throw new RuntimeException("비밀번호 불일치");
    }
}
