package com.mphasis.csp.dto.response;

import com.mphasis.csp.enums.*;
import com.mphasis.csp.model.Ticket;
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

    public static TicketResponseDTO mapToDTO(Ticket ticket) {
        return TicketResponseDTO.builder()
                .ticketId(ticket.getTicketId())
                .userId(ticket.getUser().getUserId())
                .category(ticket.getTicketCategory())
                .subcategory(ticket.getTicketSubcategory())
                .description(ticket.getDescription())
                .status(ticket.getTicketStatus())
                .dateOfSubmission(ticket.getDateOfSubmission())
                .dateOfUpdate(ticket.getDateOfUpdate())
                .build();
    }
}