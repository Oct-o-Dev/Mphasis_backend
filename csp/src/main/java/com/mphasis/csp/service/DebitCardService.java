package com.mphasis.csp.service;

import com.mphasis.csp.dto.DebitCardResponseDTO;
import com.mphasis.csp.dto.UpdateDebitCardRequestDTO;
import com.mphasis.csp.enums.DebitCardStatus;
import com.mphasis.csp.model.DebitCard;
import com.mphasis.csp.model.User;
import com.mphasis.csp.repository.DebitCardRepository;
import com.mphasis.csp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DebitCardService implements IDebitCardService {

    private final DebitCardRepository debitCardRepository;
    private final UserRepository userRepository;

    // ✅ CREATE CARD
    @Override
    public DebitCardResponseDTO createCard(String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String cardNumber = generateCardNumber();

        DebitCard card = DebitCard.builder()
                .debitCardNumber(cardNumber)
                .user(user)
                .build();

        DebitCard savedCard = debitCardRepository.save(card);

        return mapToDTO(savedCard); // ✅ return DTO
    }

    // ✅ GET CARDS
    @Override
    public List<DebitCardResponseDTO> getCards(String email) {

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<DebitCard> cards;

        if (user.getRole().equals("CUSTOMER")) {
            cards = debitCardRepository.findByUser(user);
        } else {
            cards = debitCardRepository.findAll();
        }

        return cards.stream()
                .map(this::mapToDTO) // ✅ convert list to DTO
                .toList();
    }

    // ✅ UPDATE CARD
    @Override
    public DebitCardResponseDTO updateCard(String cardNumber, UpdateDebitCardRequestDTO request) {

        DebitCard card = debitCardRepository.findById(cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (request.getStatus() != null) {
            try {
                card.setDebitCardStatus(
                        DebitCardStatus.valueOf(request.getStatus())
                );
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status value");
            }
        }

        DebitCard updatedCard = debitCardRepository.save(card);

        return mapToDTO(updatedCard); // ✅ return DTO
    }

    // ✅ BLOCK CARD
    @Override
    public void blockCard(String cardNumber) {

        DebitCard card = debitCardRepository.findById(cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setDebitCardStatus(DebitCardStatus.PERMANENTLYCLOSED);

        debitCardRepository.save(card);
    }

    // ✅ UTILITY: Generate Card Number
    private String generateCardNumber() {
        return "DC" + System.currentTimeMillis();
    }

    // ✅ DTO MAPPER
    private DebitCardResponseDTO mapToDTO(DebitCard card) {

        return DebitCardResponseDTO.builder()
                .debitCardNumber(card.getDebitCardNumber())
                .status(card.getDebitCardStatus().name())
                .build();
    }
}