package com.mphasis.csp.controller;

import com.mphasis.csp.dto.response.UserProfileResponseDTO;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponseDTO dto = UserProfileResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .emailId(user.getEmailId())
                .build();

        return ResponseEntity.ok(dto);
    }
}