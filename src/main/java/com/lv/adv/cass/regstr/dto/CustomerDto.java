package com.lv.adv.cass.regstr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    private boolean isCompany;

    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name must not exceed 100 characters")
    private String companyName;

    @Size(max = 20, message = "Registration number must not exceed 20 characters")
    private String registrationNumber;

    @NotBlank(message = "Declared address is required")
    private String declaredAddress;

    private String actualAddress;

    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    private String phone;

    @Email(message = "Email must be valid")
    private String email;

    private String personIdentifier; // Identifier for the person associated with the customer

    private String representativeIdentifier; // Identifier for the representative
}
