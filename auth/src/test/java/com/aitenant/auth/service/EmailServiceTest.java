package com.aitenant.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setDetails(){
        ReflectionTestUtils.setField(emailService,"fromEmail","kishore@gmail.com");
    }

    @Test
    void shouldSentOtpEmailSuccessfully(){
        String toEmail = "kishoretoemail@gmail.com";
        String token = "123456";
        emailService.sendOtpEmail(toEmail,token);

        ArgumentCaptor<SimpleMailMessage> messageArgumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender,times(1)).send(messageArgumentCaptor.capture());

        SimpleMailMessage message = messageArgumentCaptor.getValue();
        assertEquals("Password Reset Verification Code",message.getSubject());
        assertEquals("kishore@gmail.com", message.getFrom());
        assertEquals("kishoretoemail@gmail.com", message.getTo()[0]);
    }
}
