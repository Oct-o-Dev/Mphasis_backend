
package com.mphasis.csp.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mphasis.csp.enums.TicketCategory;
import com.mphasis.csp.enums.TicketSubcategory;
import com.mphasis.csp.enums.TicketStatus;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private TicketCategory ticketCategory;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "subcategory", nullable = false, length = 50)
    private TicketSubcategory ticketSubcategory;

    @NotBlank
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    // ✅ ONLY ONE STATUS FIELD (KEEP THIS)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private TicketStatus ticketStatus = TicketStatus.PENDING_CRO;

    @Column(name = "date_of_submission", nullable = false, updatable = false)
    private LocalDateTime dateOfSubmission;

    @Column(name = "date_of_update")
    private LocalDateTime dateOfUpdate;

    @OneToMany(
            mappedBy = "ticket",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;


//    @Builder.Default
//    private List<TicketService> services = new ArrayList<>();
@OneToMany(
        mappedBy = "ticket",
        cascade = CascadeType.ALL,
        orphanRemoval = true
)
@Builder.Default
private List<TicketService> services = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (dateOfSubmission == null) {
            dateOfSubmission = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        dateOfUpdate = LocalDateTime.now();
    }
}