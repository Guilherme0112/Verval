package com.br.verval.utils;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Util {
    
    private static final PasswordEncoder passworEncoder = new BCryptPasswordEncoder();

    /***
     * Método que criptografa a senha
     * 
     * @param senha Senha do usuário (Sem criptografia)
     * @return Hash da senha
     */
    public static String Bcrypt(String senha){
        return passworEncoder.encode(senha);
    }

    /***
     * Verifica se a senha corresponde ao hash
     * 
     * @param hash Senha criptografa no banco de dados
     * @param senha Senha enviada pelo o usuário (Sem criptografia)
     * @return Retorna um Boolean, TRUE caso seja válida, FALSE caso não seja válida
     */
    public static Boolean checkPassword(String hash, String senha){
        return passworEncoder.matches(senha, hash);
    }

    /***
     * Gera  "tokens" aleatórios
     * 
     * @return Token gerado de forma aleatória
     */
    public static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
