package com.mphasis.csp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DebitCardResponseDTO {

    private String debitCardNumber;
    private String status;
}