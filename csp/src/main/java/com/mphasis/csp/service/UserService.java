package com.mphasis.csp.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mphasis.csp.dto.RegisterRequest;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(RegisterRequest req) {

        // ✅ Check password match
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // ✅ Check email exists
        if (repo.findByEmailId(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // ✅ Create new user
        User user = new User();

        // ✅ ✅ FIXED HERE 🔥 (IMPORTANT)
        user.setUsername(req.getUsername());     // ✅ correct field
        user.setFirstName(req.getFirstName());   // ✅ correct field

        user.setEmailId(req.getEmail());
        user.setPasswordHash(encoder.encode(req.getPassword())); // ✅ encrypted password
        user.setRole("CUSTOMER");

        // ✅ Other fields
        user.setLastName(req.getLastName());
        user.setPhoneNo(req.getPhoneNo());

        return repo.save(user);
    }
}
