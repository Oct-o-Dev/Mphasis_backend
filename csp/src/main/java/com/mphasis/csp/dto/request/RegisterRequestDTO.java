package com.mphasis.csp.dto.request;


import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNo;
}