package com.br.verval.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Util {
    
    private static final PasswordEncoder passworEncoder = new BCryptPasswordEncoder();
    private static final String SECRET_KEY = "3202B2176B31BCF2";

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
     * Gera um token a partir do e-mail
     * 
     * @param email String que será criptografada
     * @return  Token (Email criptografado)
     * @throws Exception
     */
    public static String gerarTokenEmail(String email) throws Exception{
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(email.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /***
     * Descriptografa o token 
     * 
     * @param encryptedData Token que será descriptografado
     * @return Retornar o email que estava criptografado
     * @throws Exception
     */
    public static String decrypt(String token) throws Exception {
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(token));
        return new String(decryptedBytes);
    }
}
