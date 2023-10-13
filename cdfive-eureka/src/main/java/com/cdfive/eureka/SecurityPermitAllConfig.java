//package com.cdfive.eureka;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
///**
// * @author cdfive
// */
//@Configuration
//public class SecurityPermitAllConfig {
//
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.authorizeRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll())
//                .csrf().disable().build();
//    }
//}
