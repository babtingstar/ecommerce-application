package com.kht.ecommerce.ecommerce_application.service;

import com.kht.ecommerce.ecommerce_application.vo.VerificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationService {

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> verificationCodes = new HashMap<>();

    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 랜덤 숫자 생성
        return String.valueOf(code);
    }

    public void sendEmail(String recipientEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + code);

        mailSender.send(message);
        System.out.println("Email sent successfully to: " + recipientEmail);
    }

    public void saveVerificationCode(String email, String code) {
        verificationCodes.put(email.toLowerCase(), code);
    }

    public boolean verifyCodeWithVO(VerificationRequest request) {
        String email = request.getEmail().toLowerCase();
        String inputCode = request.getCode();
        String savedCode = verificationCodes.get(email);
        return inputCode.equals(savedCode);
    }

}
