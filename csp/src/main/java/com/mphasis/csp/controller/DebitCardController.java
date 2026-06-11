package com.mphasis.csp.controller;

import com.mphasis.csp.dto.response.DebitCardResponseDTO;
import com.mphasis.csp.dto.request.UpdateDebitCardRequestDTO;
import com.mphasis.csp.service.IDebitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class DebitCardController {

    private final IDebitCardService debitCardService;

    // Create Card (CUSTOMER only)
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<DebitCardResponseDTO> createCard(Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(debitCardService.createCard(email));
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','CRO','ADMIN')")
    @GetMapping
    public ResponseEntity<List<DebitCardResponseDTO>> getCards(Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(debitCardService.getCards(email));
    }

    @PreAuthorize("hasAnyRole('CRO','ADMIN')")
    @PutMapping("/{cardNumber}")
    public ResponseEntity<DebitCardResponseDTO> updateCard(
            @PathVariable String cardNumber,
            @RequestBody UpdateDebitCardRequestDTO request,
            Authentication authentication) {

        return ResponseEntity.ok(
                debitCardService.updateCard(cardNumber, request)
        );
    }

    @PreAuthorize("hasAnyRole('CRO','ADMIN')")
    @PatchMapping("/{cardNumber}/block")
    public ResponseEntity<String> blockCard(@PathVariable String cardNumber) {

        debitCardService.blockCard(cardNumber);
        return ResponseEntity.ok("Card blocked successfully");
    }
}
