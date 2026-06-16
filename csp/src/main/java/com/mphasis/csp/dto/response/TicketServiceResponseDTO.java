package com.mphasis.csp.dto.response;

import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketServiceResponseDTO {

    @NotNull
    private Integer serviceId;

    @NotNull
    private Integer ticketId;

    @NotNull
    private ServiceAction serviceAction;

    @NotNull
    private String comment;

    @NotNull
    private TicketStatus oldStatus;

    @NotNull
    private TicketStatus newStatus;

    @NotNull
    private LocalDateTime dateOfService;

    @NotNull
    private Integer servicedBy;
}
