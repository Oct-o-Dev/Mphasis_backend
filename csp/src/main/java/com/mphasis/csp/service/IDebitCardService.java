package com.mphasis.csp.service;

import com.mphasis.csp.dto.response.DebitCardResponseDTO;
import com.mphasis.csp.dto.request.UpdateDebitCardRequestDTO;

import java.util.List;

public interface IDebitCardService {

    DebitCardResponseDTO createCard(String email);

    List<DebitCardResponseDTO> getCards(String email);

    DebitCardResponseDTO updateCard(String cardNumber, UpdateDebitCardRequestDTO request);

    void blockCard(String cardNumber);
}