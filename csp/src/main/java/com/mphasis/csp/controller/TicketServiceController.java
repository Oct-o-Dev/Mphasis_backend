package com.mphasis.csp.controller;

import com.mphasis.csp.dto.request.RaiseServiceRequestDTO;
import com.mphasis.csp.dto.response.TicketResponseDTO;

import com.mphasis.csp.service.ITicketServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketServiceController {

    private final ITicketServiceService ticketServiceService;

    // Customer Raise Service: For Commenting, Escalating back to CRO
    @PostMapping("/customer/raiseService")
    public ResponseEntity<?> customerRaiseService (
            @Valid @RequestBody RaiseServiceRequestDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TicketResponseDTO updatedTicket = ticketServiceService.raiseService(dto, email);
        return ResponseEntity.ok(updatedTicket);
    }

    //  CRO / MANAGER : Raise Service
    @PostMapping("/cro/raiseService")
    public ResponseEntity<?> croRaiseService(
            @Valid @RequestBody RaiseServiceRequestDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TicketResponseDTO updatedTicket = ticketServiceService.raiseService(dto, email);
        return ResponseEntity.ok(updatedTicket);
    }
}