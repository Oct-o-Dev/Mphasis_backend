package com.mphasis.csp.dto.request;

import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RaiseServiceRequestDTO {

    @NotNull
    private Integer ticketId;

    @NotNull
    private ServiceAction serviceAction;

    // Comment by Customer/CRO/Manager is not mandatory and does not update state
    private String comment;
}
