package com.mphasis.csp.model;



import jakarta.persistence.*;
import lombok.Data;


@Data
@Table
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    @Column(name = "username")   // ✅ Explicit mapping (fixes your issue)
    private String username;


    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String role;
}
