package com.br.verval.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa CSRF
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/*").authenticated()
            .requestMatchers("/api/auth/login").permitAll() 
            )
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            
            // .exceptionHandling(exceptions -> exceptions
            //     .authenticationEntryPoint((request, response, authException) -> {
            //         response.setStatus(401);
                    
            //         response.setContentType("application/json");
            //         response.getWriter().println("""
            //             {
            //                 error: "Acesso negado.",
            //                 status: 401
            //             }""");
                // }))
                ; 
            

        return http.build();
    }
}