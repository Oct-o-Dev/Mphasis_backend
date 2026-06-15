package com.mphasis.csp.service;

import com.mphasis.csp.dto.request.*;
import com.mphasis.csp.dto.response.CroDashboardResponseDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;

import java.util.List;

public interface ITicketService {

    TicketResponseDTO raiseTicket(RaiseTicketRequestDTO requestDTO, String email);
    TicketResponseDTO getTicket(GetTicketRequestDTO requestDTO, String email);
    List<TicketResponseDTO> getTickets(String requestDTO, String email);
    List<TicketResponseDTO> getAllTickets(String requestDTO);
    List<TicketResponseDTO> getTicketsByStatus(String status);
    List<CroDashboardResponseDTO> getCroDashboard();
}