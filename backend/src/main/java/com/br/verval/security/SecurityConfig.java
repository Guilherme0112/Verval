package com.br.verval.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.br.verval.filter.JwtAuthenticationFilter;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String KEY_JWT = dotenv.get("KEY_JWT");
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY_JWT.getBytes());
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {

        return NimbusJwtDecoder.withSecretKey(SECRET_KEY).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers("/api/auth/validate").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable());
        
        return http.build();
    }

}
