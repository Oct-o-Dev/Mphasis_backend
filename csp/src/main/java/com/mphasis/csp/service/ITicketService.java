package com.mphasis.csp.service;

import com.mphasis.csp.dto.request.*;
import com.mphasis.csp.dto.response.CroDashboardResponseDTO;
import com.mphasis.csp.dto.response.TicketHistoryResponseDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ITicketService {

    TicketResponseDTO raiseTicket(RaiseTicketRequestDTO requestDTO, String email);
    TicketResponseDTO getTicket(Integer ticketId, String email);
    TicketHistoryResponseDTO getTicketHistory(Integer ticketId, Authentication authentication);
    List<TicketResponseDTO> getTicketsRaisedBy(String email);
    List<TicketResponseDTO> getTicketsAssignedTo(String email);
    List<TicketResponseDTO> getAllTickets();
    List<TicketResponseDTO> getTicketsByStatus(String status);
    List<CroDashboardResponseDTO> getCroDashboard();
}