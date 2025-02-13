package com.br.verval.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class JWTUtil {
    
    private static final Dotenv dotenv = Dotenv.load();
    private static final String KEY_JWT = dotenv.get("KEY_JWT");
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY_JWT.getBytes());
    private static final long TIME_EXPIRATION = 4 * 60 * 60 * 1000;
    
    /***
     * Gera o token JWT
     *  
     * @param email Email do usuário
     * @return Retorna o token
     */
    public static String gerarToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + TIME_EXPIRATION))
        .signWith(SECRET_KEY)
        .compact();
    }

        public static Claims decodeJWT(String token) {
        String[] splitToken = token.split("\\."); // Divide o JWT (header.payload.signature)
    
        return Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(splitToken[0] + "." + splitToken[1] + ".") // Ignora a assinatura
                .getBody();
    }

    /***
     * Verifica se o token está expirado
     * 
     * @param token Token que será verificado
     * @return Retorna FALSE caso esteja expirado e TRUE caso não esteja
     */
    public static Boolean tokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();

        return expiration.before(new Date());
    }

    /***
     * Busca o cookie que tem o token JWT
     * 
     * @param request Requisição
     * @param cookieName Nome do cookie
     * @return Retorna NULL caso não encontre o cookie, caso exista ele retorna o valor
     */
    public static String getCookie(HttpServletRequest request, String cookieName){
        
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        
        return null;
    }

    
}
