package com.example.dogstar.config;

import com.example.dogstar.domain.Member;
import com.example.dogstar.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig{

    private final UserDetailsService memberService;

    //
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())
//                .requestMatchers("/static/**");
//    }

    //
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.
                authorizeRequests()
                    .requestMatchers("/login", "/join", "").permitAll()
//                    .anyRequest().authenticated()
                    .anyRequest().hasAnyRole("user")
                .and()
//                .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/boards")
//                .and()
//                .logout()
//                    .logoutSuccessUrl("/login")
//                    .invalidateHttpSession(true)
//                .and()
                .csrf().disable()
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService memberService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(memberService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
