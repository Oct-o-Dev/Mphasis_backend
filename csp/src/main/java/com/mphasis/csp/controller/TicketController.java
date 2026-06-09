package com.mphasis.csp.controller;

import com.mphasis.csp.dto.*;
import com.mphasis.csp.service.ITicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class TicketController {

    private final ITicketService ticketService;

    // Raise Ticket
    @PostMapping("/raiseTicket")
    public TicketResponseDTO raiseTicket(
            @Valid @RequestBody RaiseTicketRequestDTO requestDTO) {
        return ticketService.raiseTicket(requestDTO);
    }

    // Get One Ticket
    @PostMapping("/getTicket")
    public TicketResponseDTO getTicket(
            @Valid @RequestBody GetTicketRequestDTO requestDTO) {
        return ticketService.getTicket(requestDTO);
    }

    // Get All Tickets
    @PostMapping("/getTickets")
    public List<TicketResponseDTO> getTickets(
            @Valid @RequestBody GetTicketsRequestDTO requestDTO) {
        return ticketService.getTickets(requestDTO);
    }
}