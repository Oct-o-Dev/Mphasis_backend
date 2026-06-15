package com.mphasis.csp.dto.request;

import com.mphasis.csp.enums.TicketCategory;
import com.mphasis.csp.enums.TicketSubcategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RaiseTicketRequestDTO {

    @NotNull
    private TicketCategory category;

    @NotNull
    private TicketSubcategory subcategory;

    @NotBlank
    private String description;

    @Size(min = 4, max = 4)
    private String debitCardLast4Digits;
}