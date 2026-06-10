package com.mphasis.csp.service;

import com.mphasis.csp.dto.*;
import com.mphasis.csp.model.Ticket;

import java.util.List;

public interface ITicketService {

    com.mphasis.csp.dto.TicketResponseDTO raiseTicket(com.mphasis.csp.dto.RaiseTicketRequestDTO requestDTO);

    com.mphasis.csp.dto.TicketResponseDTO getTicket(GetTicketRequestDTO requestDTO);

    List<com.mphasis.csp.dto.TicketResponseDTO> getTickets(GetTicketsRequestDTO requestDTO);

    //New Method
    //com.mphasis.csp.dto.TicketResponseDTO updateTicketService(com.mphasis.csp.dto.RaiseTicketServiceDTO dto);
}