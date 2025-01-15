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
                .requestMatchers("/auth/login").permitAll() 
                .requestMatchers("/api/*").authenticated() // Requer autenticação para acessar a API
            )
            .formLogin(form -> form.disable()) // Desativa a tela de login padrão
            .httpBasic(httpBasic -> httpBasic.disable()); // Desativa autenticação HTTP básica

        return http.build();
    }
}