package com.mphasis.csp.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    private Long userId;

    private String category;
    private String subcategory;
    private String description;
    private String status;

    private LocalDateTime dateOfSubmission;
    private LocalDateTime dateOfUpdate;
}
