package com.mphasis.csp.dto.response;

import com.mphasis.csp.enums.ServiceAction;
import com.mphasis.csp.enums.TicketStatus;
import com.mphasis.csp.model.TicketService;
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

    private Integer serviceId;
    private Integer ticketId;
    private ServiceAction serviceAction;
    private String comment;
    private TicketStatus oldStatus;
    private TicketStatus newStatus;
    private LocalDateTime dateOfService;
    private Integer servicedBy;

    public static TicketServiceResponseDTO mapToDTO(TicketService service) {
        return TicketServiceResponseDTO.builder()
                .serviceId(service.getServiceId())
                .ticketId(service.getTicket().getTicketId())
                .serviceAction(service.getServiceAction())
                .comment(service.getComment())
                .oldStatus(service.getOldStatus())
                .newStatus(service.getNewStatus())
                .dateOfService(service.getDateOfService())
                .servicedBy(Math.toIntExact(service.getCro().getUserId()))
                .build();
    }
}
