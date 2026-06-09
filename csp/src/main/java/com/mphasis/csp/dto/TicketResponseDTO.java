package com.mphasis.csp.dto;

import com.mphasis.csp.enums.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponseDTO {

    private Integer ticketId;
    private Long userId;
    private TicketCategory category;
    private TicketSubcategory subcategory;
    private String description;
    private TicketStatus status;
    private LocalDateTime dateOfSubmission;
    private LocalDateTime dateOfUpdate;
}