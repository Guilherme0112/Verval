package com.br.verval.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.verval.utils.JWTUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/")
public class UsuarioController {


    @GetMapping("/user/profile")
    public ResponseEntity<?> user(HttpServletRequest request){
        
        Claims token = JWTUtil.decodeJWT(request.getHeader("Authorization").replace("Bearer ", ""));

        return ResponseEntity.ok().body(Map.of("status", token));
        
    } 

}
