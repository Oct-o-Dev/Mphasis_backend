package com.mphasis.csp.security;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())   // ✅ FIXED arrow
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        //Allow Login/Register without auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // ✅ ROLE-based access starts here

                        //Only Admin
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        //AGENT+ADMIN
                        .requestMatchers("/api/agent/**").hasAnyRole("AGENT", "ADMIN")

                        //only Customer
                        .requestMatchers("/api/customer/**").hasRole("CUSTOMER")

                        //everything else needs login
                        .anyRequest().authenticated()   // ✅ for testing
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
