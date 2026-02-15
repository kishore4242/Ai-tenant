package com.aitenant.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOtpEmail(String toEmail, String token) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Password Reset Verification Code");
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setText(
                            "Hello,\n\n" +
                            "We received a request to reset your password.\n\n" +
                            "Your One-Time Password (OTP) is:\n\n" +
                            token + "\n\n" +
                            "This OTP is valid for 10 minutes.\n\n" +
                            "If you did not request a password reset, please ignore this email.\n\n" +
                            "Thank you."
            );
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
