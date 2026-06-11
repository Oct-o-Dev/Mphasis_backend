package com.mphasis.csp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetTicketRequestDTO {

    @NotNull
    private Integer ticketId;
}