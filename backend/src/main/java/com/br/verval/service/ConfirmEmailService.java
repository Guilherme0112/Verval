package com.br.verval.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.verval.models.ConfirmEmail;
import com.br.verval.repositorys.ConfirmEmailRepository;

@Service
public class ConfirmEmailService {

    private final ConfirmEmailRepository confirmEmailRepository;

    public ConfirmEmailService(ConfirmEmailRepository confirmEmailRepository){
        this.confirmEmailRepository = confirmEmailRepository;
    }


    /***
     *  Pega todos os tokens do banco de dados e verifica se já expirou, caso tenha expirado ele deleta
     */
    public void verificationAllTokens(){

        List<ConfirmEmail> allTokens = confirmEmailRepository.findAll();

        for (ConfirmEmail confirmEmail : allTokens) {
            if(confirmEmail.getExpire_in().isAfter(LocalDateTime.now())){
                confirmEmailRepository.delete(confirmEmail);
            }
        }
    }
}
