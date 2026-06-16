package com.mphasis.csp.controller;

import com.mphasis.csp.dto.request.GetTicketRequestDTO;
import com.mphasis.csp.dto.request.RaiseTicketRequestDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;
import com.mphasis.csp.service.ITicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketController {

    @Autowired
    private final ITicketService ticketService;

    // CUSTOMER : raise ticket
    @PostMapping("/customer/raiseTicket")
    public TicketResponseDTO raiseTicket(
            @Valid @RequestBody RaiseTicketRequestDTO requestDTO,
            Authentication authentication) {

        String email = authentication.getName();
        return ticketService.raiseTicket(requestDTO, email);
    }

    // CUSTOMER : get their single ticket
    @GetMapping("/customer/getTicket")
    public TicketResponseDTO getTicket(
            @Valid @RequestBody GetTicketRequestDTO requestDTO,
            Authentication authentication) {

        String email = authentication.getName();
        return ticketService.getTicket(requestDTO, email);
    }

    // CUSTOMER : get all their tickets
    @GetMapping("/customer/getTicketsRaisedBy")
    public List<TicketResponseDTO> getTickets(
            String request,
            Authentication authentication) {

        String email = authentication.getName();
        return ticketService.getTicketsRaisedBy(email);
    }

    // CRO / MANAGER : all tickets assigned to that CRO
    @GetMapping("/cro/getTicketsAssignedTo")
    public List<TicketResponseDTO> getAllTicketsAssignedToCRO(
            String request,
            Authentication authentication) {

        String email = authentication.getName();
        return ticketService.getTicketsAssignedTo(email);
    }

    // MANAGER : get ALL the tickets
    @GetMapping("/admin/getAllTickets")
    public List<TicketResponseDTO> getAllTickets(
            String request,
            Authentication authentication
    ) {
        return ticketService.getAllTickets();
    }
}