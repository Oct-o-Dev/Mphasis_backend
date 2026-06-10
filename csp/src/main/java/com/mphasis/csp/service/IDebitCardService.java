package com.mphasis.csp.service;

import com.mphasis.csp.dto.DebitCardResponseDTO;
import com.mphasis.csp.dto.UpdateDebitCardRequestDTO;
import com.mphasis.csp.model.DebitCard;

import java.util.List;

public interface IDebitCardService {

    DebitCardResponseDTO createCard(String email);

    List<DebitCardResponseDTO> getCards(String email);

    DebitCardResponseDTO updateCard(String cardNumber, UpdateDebitCardRequestDTO request);

    void blockCard(String cardNumber);
}