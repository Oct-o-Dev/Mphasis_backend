package com.mphasis.csp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistoryResponseDTO {

    private TicketResponseDTO ticketDetails;
    List<TicketServiceResponseDTO> ticketHistory;
}
