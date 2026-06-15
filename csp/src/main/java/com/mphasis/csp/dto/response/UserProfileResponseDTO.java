package com.mphasis.csp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponseDTO {

    private String firstName;
    private String lastName;
    private String emailId;
}
