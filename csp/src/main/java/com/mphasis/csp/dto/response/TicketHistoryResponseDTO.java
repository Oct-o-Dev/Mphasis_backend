package com.mphasis.csp.dto.response;

import com.mphasis.csp.model.Ticket;
import com.mphasis.csp.model.TicketService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketHistoryResponseDTO {

    private TicketResponseDTO ticketDetails;
    List<TicketServiceResponseDTO> ticketHistory;

    public static TicketHistoryResponseDTO mapToDTO(Ticket ticket, List<TicketService> serviceHistory) {
        return TicketHistoryResponseDTO.builder()
                .ticketDetails(
                        TicketResponseDTO.mapToDTO(ticket)
                )
                .ticketHistory(
                        serviceHistory.stream()
                                .map(TicketServiceResponseDTO::mapToDTO)
                                .toList()
                )
                .build();
    }
}
