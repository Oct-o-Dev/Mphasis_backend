
package com.mphasis.csp.service;

import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender; // ✅ email enable

    // ✅ In-memory storage (NO DB)
    private Map<String, TokenData> tokenStore = new HashMap<>();

    // ✅ Forgot Password
    public void forgotPassword(String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        // ✅ store in memory
        TokenData data = new TokenData(email, LocalDateTime.now().plusMinutes(20));
        tokenStore.put(token, data);

        // ✅ create reset link (backend based)
        String resetLink =
                "http://localhost:4200/reset-password?token=" + token;

        // ✅ Console output
        System.out.println("===== RESET PASSWORD INFO =====");
        System.out.println("Email: " + email);
        System.out.println("Token: " + token);
        System.out.println("Expiry: " + data.expiryDate);
        System.out.println("Reset Link: " + resetLink);
        System.out.println("===============================");

        // ✅ Send mail
        sendEmail(email, resetLink);
    }

    // ✅ Reset Password
    public void resetPassword(String token, String newPassword) {

        TokenData data = tokenStore.get(token);

        if (data == null) {
            throw new RuntimeException("Invalid token");
        }

        if (data.expiryDate.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findByEmailId(data.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // ✅ remove after use
        tokenStore.remove(token);

        System.out.println("✅ Password updated for: " + data.email);
    }

    // ✅ Email function
    private void sendEmail(String to, String link) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject("Reset Password");
        mail.setText("Click this link to reset your password:\n\n" + link);

        mailSender.send(mail);
    }

    // ✅ helper class
    static class TokenData {
        String email;
        LocalDateTime expiryDate;

        public TokenData(String email, LocalDateTime expiryDate) {
            this.email = email;
            this.expiryDate = expiryDate;
        }
    }
}