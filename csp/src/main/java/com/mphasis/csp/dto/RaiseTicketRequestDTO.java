package com.mphasis.csp.dto;

import com.mphasis.csp.enums.TicketCategory;
import com.mphasis.csp.enums.TicketSubcategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RaiseTicketRequestDTO {

    @NotNull
    private TicketCategory category;

    @NotNull
    private TicketSubcategory subcategory;

    @NotBlank
    private String description;
}