package com.mphasis.csp.dto;

import com.mphasis.csp.enums.ServiceType;
import com.mphasis.csp.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RaiseTicketServiceDTO {

    @NotNull
    private Integer ticketId;

    @NotNull
    private ServiceType serviceType;

    @NotNull
    private TicketStatus newStatus;

    private String comment;
}