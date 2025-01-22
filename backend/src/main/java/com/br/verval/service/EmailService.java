package com.br.verval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mail;

    private static final Dotenv dotenv = Dotenv.load();
    private static final String my_email = dotenv.get("USER_EMAIL");


    /***
     * Envia o e-mail para o usuário do parametro
     * 
     * @param email Email do usuário
     * @param titulo Título do assunto
     * @param html HTML onde está o template do email
     * @throws Exception
     */
    public void sendEmail(String email, String titulo, String html) throws Exception{
        try{

            MimeMessage mimeMessage = mail.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(titulo);
            helper.setText(html, true);
            helper.setFrom(my_email);

            mail.send(mimeMessage);
            System.out.println("E-mail enviado com sucesso");

        } catch (Exception e) {

            System.out.println("Erro ao enviar e-mail | " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

}
