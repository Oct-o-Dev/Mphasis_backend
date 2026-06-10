package com.mphasis.csp.dto;

import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RaiseTicketServiceDTO {

    @NotNull
    private Integer ticketId;

    @NotNull
    private ServiceAction serviceAction;

    private String comment;

    private TicketStatus newStatus;
}