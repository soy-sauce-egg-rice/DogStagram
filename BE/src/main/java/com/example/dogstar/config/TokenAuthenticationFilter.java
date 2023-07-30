package com.example.dogstar.config;

import com.example.dogstar.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    private final static String HEADER_AUTHORIZATION = "Authorization";

    private final static String TOKEN_PREFIX = "Bearer ";
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        String token = getAccessToken(authorizationHeader);
        log.info("filter is running");

        if (tokenProvider.validToken(token)) {
            Long memberId = tokenProvider.getMemberId(token);
//            Authentication authentication = tokenProvider.getAuthentication(token);
            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                memberId,null, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            );
            log.info("authentication : {}",authentication);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

//        if (authorizationHeader == null ||
//                !authorizationHeader.startsWith(TOKEN_PREFIX)) {
//            filterChain.doFilter(request, response);
//            return;
//        }


        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}

