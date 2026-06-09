package com.mphasis.csp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetTicketsRequestDTO {

    @NotNull
    private Long userId;
}