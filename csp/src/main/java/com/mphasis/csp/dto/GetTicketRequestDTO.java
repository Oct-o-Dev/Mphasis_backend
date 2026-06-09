package com.mphasis.csp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetTicketRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Integer ticketId;
}