package com.br.verval.service;

import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.br.verval.models.EmailRequest;
import com.br.verval.models.Usuario;
import com.br.verval.repositorys.EmailRequestRepository;

@Service
public class EmailRequestService {

    private final EmailRequestRepository emailRequestRepository;
    private final EmailService emailService;

    public EmailRequestService(EmailRequestRepository emailRequestRepository,
            EmailService emailService) {
        this.emailRequestRepository = emailRequestRepository;
        this.emailService = emailService;
    }

    /***
     * Obtém todos as requisições e deleta aquelas que não são mais válidas
     */
    public void verifyAllRegisters() {

        List<EmailRequest> allResquests = emailRequestRepository.findAll();

        for (EmailRequest emailRequest : allResquests) {
            if(emailRequest.getRequirido_em().until(LocalDateTime.now(), ChronoUnit.MINUTES) > 1){
                emailRequestRepository.delete(emailRequest);
            }
        }
    }

    /***
     * Verifica se existe alguma requisição de envio de email antes de enviar o email para evitar envios duplicados
     * 
     * @param usuario Objeto do usuário que receberá o email
     * @param email String do template do email que será enviado
     * @return False caso exista requisições com menos de um minuto ou exceptions
     * @throws Exception
     */
    public synchronized Boolean verfifyEmailRequest(Usuario usuario, String email) throws Exception {

        try {

            List<EmailRequest> email_request = emailRequestRepository.findByEmail(usuario.getEmail());

            if (!email_request.isEmpty()) {

                // Pega o objeto da requisição e quando a requisição foi feita
                EmailRequest email_request_obj = email_request.get(0);
                LocalDateTime requirido_em = email_request_obj.getRequirido_em();

                // Retorna uma mensagem caso já tenha uma requisição
                if (requirido_em.until(LocalDateTime.now(), ChronoUnit.MINUTES) < 1) {

                    return false;

                } else {

                    this.verifyAllRegisters();

                }
            } else {

                // Envia um e-mail de confirmação para o usuário
                emailService.sendEmail(usuario.getEmail(), "Verificação do e-mail", email);

                 // Gera um registro de requisição
                emailRequestRepository.save(new EmailRequest(usuario.getEmail()));
            }

            return true;

        } catch (Exception e) {

            System.out.println("Erro_Exception: " + e.getMessage());
            return false;
        }
    }
}
