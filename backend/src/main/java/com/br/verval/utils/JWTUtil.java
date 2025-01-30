package com.br.verval.utils;


import java.util.Date;

import javax.crypto.SecretKey;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {
    
    private static final Dotenv dotenv = Dotenv.load();
    private static final String KEY_JWT = dotenv.get("KEY_JWT");
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY_JWT.getBytes());
    private static final long TIME_EXPIRATION = 4 * 60 * 60 * 1000;
    
    public static String gerarToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + TIME_EXPIRATION))
        .signWith(SECRET_KEY)
        .compact();
    }

    public static Boolean tokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();

        return expiration.before(new Date());
    }

    
}
