package com.mphasis.csp.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.mphasis.csp.dto.LoginRequestDTO;
import com.mphasis.csp.dto.RegisterRequestDTO;
import com.mphasis.csp.model.User;
import com.mphasis.csp.security.JwtUtil;
import com.mphasis.csp.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request,
                           HttpServletResponse response) {

        try {
            // ✅ Save user
            User user = userService.register(request);

            // ✅ Generate JWT safely
            String token = jwtUtil.generateToken(user.getEmailId(), user.getRole());

            // ✅ Store token in Cookie
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(86400);

            response.addCookie(cookie);

            return "✅ User Registered Successfully";

        } catch (Exception e) {
            e.printStackTrace();  // ✅ show exact error in console
            return "❌ Error: " + e.getMessage();
        }
    }

    // ✅ ✅ LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request,
                        HttpServletResponse response) {

        try {
            // ✅ Authenticate user
            User user = userService.login(request);

            // ✅ Generate JWT
            String token = jwtUtil.generateToken(user.getEmailId(), user.getRole());

            // ✅ Store token in Cookie (same as register)
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(86400);

            response.addCookie(cookie);
//            System.out.println(token);
            return token;

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error: " + e.getMessage();
        }

    }
}