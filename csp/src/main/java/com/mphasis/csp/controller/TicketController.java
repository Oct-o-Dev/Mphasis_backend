package com.mphasis.csp.controller;

import com.mphasis.csp.dto.*;
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
    @PostMapping("/customer/getTicket")
    public TicketResponseDTO getTicket(
            @Valid @RequestBody GetTicketRequestDTO requestDTO,
            Authentication authentication) {

        String email = authentication.getName();
        return ticketService.getTicket(requestDTO, email);
    }

    // CUSTOMER : get all their tickets
    @PostMapping("/customer/getTickets")
    public List<TicketResponseDTO> getTickets(
            @Valid @RequestBody GetTicketsRequestDTO requestDTO,
            Authentication authentication) {

        String email = authentication.getName();
        return ticketService.getTickets(requestDTO, email);
    }

    // CRO / MANAGER : all tickets
    @PostMapping("/cro/getTickets")
    public List<TicketResponseDTO> getAllTickets(
            @Valid @RequestBody GetAllTicketsRequestDTO requestDTO) {

        return ticketService.getAllTickets(requestDTO);
    }
}