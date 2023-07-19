package com.example.dogstar.dto;

import com.example.dogstar.domain.Board;
import com.example.dogstar.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String token;
    private String id;
    private String password;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .password(password)
                .email(email)
                .build();
    }
}
