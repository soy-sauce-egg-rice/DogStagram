package com.example.dogstar.service;

import com.example.dogstar.domain.Member;
import com.example.dogstar.dto.MemberDTO;
import com.example.dogstar.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@RequiredArgsConstructor
@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(MemberDTO memberDTO) {
        // member 객체
        Member member = memberDTO.toEntity();

        if (member == null ||
            member.getId() == null ||
            member.getPassword() == null ||
            member.getEmail() == null) throw  new RuntimeException("Invalid Arguments");
        if (memberRepository.existsById(member.getId())) {
            log.warn("Id already exists {}", member.getId());
            throw new RuntimeException("Id already exists");
        }
        if (memberRepository.existsByEmail(member.getEmail())){
            log.warn("Email already exists {}", member.getEmail()); // 실행
            throw new RuntimeException("Email already exists");
        }
        return memberRepository.save(member);
    }

    public Optional<Member> findMember (Member member) {
        return memberRepository.findById(member.getId());
    }
}
