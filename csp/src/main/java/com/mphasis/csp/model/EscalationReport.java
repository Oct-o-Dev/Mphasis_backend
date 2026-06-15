package com.mphasis.csp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "escalation_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscalationReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ✅ REQUIRED

    // Service Reference (unique)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", unique = true)
    private TicketService service;

    // Ticket reference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    // User reference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // timestamp
    @Column(name = "date_of_escalation", nullable = false)
    private LocalDateTime dateOfEscalation;
}