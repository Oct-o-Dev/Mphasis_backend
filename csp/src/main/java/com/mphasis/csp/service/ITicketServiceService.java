package com.mphasis.csp.service;

import com.mphasis.csp.dto.request.RaiseServiceRequestDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;

public interface ITicketServiceService {

    TicketResponseDTO raiseService(RaiseServiceRequestDTO dto, String email);
}
