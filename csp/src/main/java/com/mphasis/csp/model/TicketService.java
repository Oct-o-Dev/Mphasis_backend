package com.mphasis.csp.model;

import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private com.mphasis.csp.model.Ticket ticket;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 30)
    private ServiceAction serviceAction;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", length = 30)
    private TicketStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", length = 30)
    private TicketStatus newStatus;

    @Column(name = "date_of_service", nullable = false)
    private LocalDateTime dateOfService;

    /**
     * CRO who performed the action
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cro_id", nullable = false)
    private User cro;

    @PrePersist
    public void prePersist() {
        if (dateOfService == null) {
            dateOfService = LocalDateTime.now();
        }
    }
}