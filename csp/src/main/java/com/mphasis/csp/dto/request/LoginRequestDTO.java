package com.mphasis.csp.dto.request;



import lombok.Data;

@Data
public class LoginRequestDTO {
    private String emailId;
    private String password;
}
