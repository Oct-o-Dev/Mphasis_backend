package com.mphasis.csp.service;

import com.mphasis.csp.dto.*;
import com.mphasis.csp.model.Ticket;

import java.util.List;

public interface ITicketService {

    TicketResponseDTO raiseTicket(RaiseTicketRequestDTO requestDTO, String email);

    TicketResponseDTO getTicket(GetTicketRequestDTO requestDTO, String email);

    List<TicketResponseDTO> getTickets(GetTicketsRequestDTO requestDTO, String email);

    List<TicketResponseDTO> getAllTickets(GetAllTicketsRequestDTO requestDTO);


    // CRO Raise Service (NEW METHOD)
    TicketResponseDTO raiseService(RaiseServiceDTO dto);

}