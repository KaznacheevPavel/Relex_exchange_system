package ru.kaznacheev.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.kaznacheev.service.EmailService;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendEmail(String emailTo, String secretKey) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Подтверждение регистрации");
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setText("Для активации перейдите по ссылке: http://localhost:8080/api/v1/clients/activation?key=" + secretKey);
        javaMailSender.send(simpleMailMessage);
    }

}
