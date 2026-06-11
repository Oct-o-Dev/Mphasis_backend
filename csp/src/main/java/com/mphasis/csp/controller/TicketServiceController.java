package com.mphasis.csp.controller;

import com.mphasis.csp.dto.request.RaiseServiceRequestDTO;
import com.mphasis.csp.service.ITicketService;

import com.mphasis.csp.service.ITicketServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cro")
@RequiredArgsConstructor
public class TicketServiceController {

    private final ITicketServiceService ticketServiceService;

    //  CRO Raise Service (main API)
    @PostMapping("/raise-service")
    public ResponseEntity<?> raiseService(
            @Valid @RequestBody RaiseServiceRequestDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(ticketServiceService.raiseService(dto, email));
    }
}