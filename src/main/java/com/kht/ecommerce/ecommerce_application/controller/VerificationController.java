package com.kht.ecommerce.ecommerce_application.controller;


import com.kht.ecommerce.ecommerce_application.service.VerificationService;
import com.kht.ecommerce.ecommerce_application.vo.VerificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/send")
    public String sendVerificationCode(@RequestBody VerificationRequest request) {
        String email = request.getEmail();
        String code = verificationService.generateVerificationCode();
        verificationService.saveVerificationCode(email, code);
        verificationService.sendEmail(email, code);
        return "Verification code sent to: " + email;
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestBody VerificationRequest request) {
        boolean isValid = verificationService.verifyCodeWithVO(request);

        if (isValid) {
            return "Verification successful!";
        } else {
            return "Verification failed. Invalid code.";
        }
    }
}
