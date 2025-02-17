package com.br.verval.filter;

import com.br.verval.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Permitir requisições OPTIONS (para CORS)
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Permitir acesso sem autenticação para rotas públicas
        String path = httpRequest.getRequestURI();
        if (path.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        // Buscar token nos cookies
        String token = getTokenFromCookies(httpRequest);

        if (token == null) {
            sendJsonErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Token ausente ou inválido");
            return;
        }

        try {
            if (JWTUtil.tokenExpired(token)) {
                sendJsonErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Token expirado");
                return;
            }

        } catch (ExpiredJwtException e) {
            sendJsonErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Token expirado");
            return;
        } catch (Exception e) {
            sendJsonErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Erro ao validar token");
            return;
        }

        // Se o token for válido, continuar a requisição
        chain.doFilter(request, response);
    }

    private String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void sendJsonErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", message);

        String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
