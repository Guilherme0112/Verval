package com.br.verval.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String KEY_JWT = dotenv.get("KEY_JWT");
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY_JWT.getBytes());

    @Bean
    public JwtDecoder jwtDecoder() {

        return NimbusJwtDecoder.withSecretKey(SECRET_KEY).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/*").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
            )
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .oauth2ResourceServer(oauth -> oauth
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            );
        
        return http.build();
    }

    /**
     * Personaliza o conversor de autenticação JWT, caso precise mapear roles ou claims.
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Adicione personalizações ao conversor, se necessário
        return converter;
    }
}
