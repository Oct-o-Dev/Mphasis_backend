package com.mphasis.csp.dto;

import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;

import lombok.Data;

@Data
public class RaiseServiceDTO {

    // Ticket ID (jis ticket pe action lena hai)
    private Integer ticketId;

    // Action type (RETURN, ESCALATE, CLOSE, COMMENT)
    private ServiceAction serviceType;

    // CRO ka comment
    private String comment;

    // Ticket ka updated status (OPEN, CLOSED, etc.)
    private TicketStatus newStatus;

    /*// jis CRO ne action liya uska user ID
    private Integer croId;*/
}
