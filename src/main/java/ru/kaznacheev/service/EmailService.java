package ru.kaznacheev.service;

public interface EmailService {
    void sendEmail(String emailTo, String secretKey);
}
