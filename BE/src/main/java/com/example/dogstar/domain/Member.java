package com.example.dogstar.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Member implements UserDetails { // UserDetails 상속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter
    private Long id;

    @Setter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
//    @ColumnDefault("user")
    private String role;

    @Builder
    public Member(String email, String password){
        this.email = email;
        this.password = password;
        this.role = "user";
    }

    @Override // 권한 반환 부
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() { //사용자 고유 아이디 여기서는 email
        return email;
    }

    @Override // 계정 만료 여부 반환
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true : 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        // 잠금되었는지 확인하는 로직
        return true; // true : 잠금되지 않음
    }

    @Override // 비밀번호 만료 여부
    public boolean isCredentialsNonExpired() {
        return true; // 만료되지 않음
    }

    @Override // 계정 사용 여부
    public boolean isEnabled() {
        return true; // 사용 가능 
    }
}
