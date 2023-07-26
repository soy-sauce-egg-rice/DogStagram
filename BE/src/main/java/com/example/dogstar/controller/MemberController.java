package com.example.dogstar.controller;

import com.example.dogstar.domain.Member;
import com.example.dogstar.dto.MemberDTO;
import com.example.dogstar.dto.ResponseDTO;
import com.example.dogstar.service.MemberDetailsService;
import com.example.dogstar.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/")
public class MemberController {
//    @Autowired
    private final MemberService memberService;
    private final MemberDetailsService memberDetailsService;
//    private static final Logger logger= (Logger) LoggerFactory.getLogger(MemberController.class);
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDTO memberDTO) {
//        예외 처리
        try {
            if (memberDTO == null || memberDTO.getPassword() == null) throw new RuntimeException("Invalid Password value...");
            return ResponseEntity.ok().body(memberService.saveMember(memberDTO));
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO){
        try{
            if(memberDTO == null || memberDTO.getPassword() == null) throw new RuntimeException("Invalid request");
            return ResponseEntity.ok().body(memberService.login(memberDTO));
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

//    @PostMapping("/memberdetails")
//    public ResponseEntity<?> memberdetails(@RequestBody MemberDTO memberDTO){
//        return ResponseEntity.ok().body(memberDetailsService.loadUserByUsername(memberDTO.getEmail()));
//    }

}
