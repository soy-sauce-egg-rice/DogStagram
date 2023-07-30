package com.example.dogstar.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // property 값을 가져와 사용하는 애너테이션
public class JwtProperties {

     private String issuer;
     private String secretKey;
}
