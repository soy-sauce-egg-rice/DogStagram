package com.example.dogstar.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private String token;
    private String id;
    private String password;
    private String email;
    private String nickname;

}
