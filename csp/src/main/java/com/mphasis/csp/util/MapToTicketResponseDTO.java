package com.mphasis.csp.util;

import com.mphasis.csp.dto.response.TicketResponseDTO;
import com.mphasis.csp.model.Ticket;

public class MapToTicketResponseDTO {
    public static TicketResponseDTO map(Ticket ticket) {
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
