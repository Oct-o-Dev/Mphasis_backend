package com.mphasis.csp.controller;

import com.mphasis.csp.dto.RaiseServiceDTO;
import com.mphasis.csp.service.ITicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cro")
@RequiredArgsConstructor
public class TicketServiceController {

    private final ITicketService ticketService;

    //  CRO Raise Service (main API)
    @PostMapping("/raise-service")
    public ResponseEntity<?> raiseService(
            @Valid @RequestBody RaiseServiceDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(ticketService.raiseService(dto, email));
    }
}