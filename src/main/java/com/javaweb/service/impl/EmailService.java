package com.javaweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender; // Spring Boot's email sender

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            // Gửi email
            javaMailSender.send(message);
            System.out.println("Email sent successfully to: " + to);  // Log thành công
        } catch (Exception e) {
            e.printStackTrace();  // Log lỗi nếu có
            throw new RuntimeException("Failed to send OTP email.", e);
        }
    }
}
