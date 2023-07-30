package com.example.dogstar.config.jwt;

import com.example.dogstar.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    // 1 토큰 생성 메서드
    public String makeToken(Date expiry, Member member){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 type : JWT
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)                     // 내용 iat : 현재 시각
                .setExpiration(expiry)                // expiry 멤버 변수값
                .setSubject(member.getEmail())        // 내용 sub : 유저 이메일
                .claim("id", member.getId())    // 클레임 아이디
                // 서명 : 비밀값과 함께 해시값을 hs256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // 2. token 유효성 검사
    public boolean validToken(String token) {
        try{
            Jwts.parser() // 프로퍼티스 비밀값과 함께 토큰 복호화 진행 진행과정에서 에러가 발생하면 유효하지 않음 => false
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }


    // 3. 토큰 기반 인증 정보 가져오는 메서드
    /*
    * 토큰을 받아 인증 정보를 담은 Authentication 객체를 반환
    * 비밀값으로 토큰을 복호화한 뒤 클레임을 가져오는 getClaims() 를 호출.
    * 클레임 정보를 반환받아 사용자 이메일이 들어 있는 토큰 제목 sub 와 토큰 기반으로 인증 정보를 생성.*/
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(),"",authorities),
                token,
                authorities
        );
    }

    public Long getMemberId(String token) { // 4. 토큰 기반으로 사용자 id 를 가져오는 =메서드
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) { // 클레임 조회
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
