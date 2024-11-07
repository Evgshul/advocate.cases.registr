package com.lv.adv.cass.regstr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PersonDto (

    @NotEmpty(message = "Person's identifier cannot be empty.")
    @Size(min = 5, max = 12, message = "Incorrect identifier length, should from 5 to 12 characters")
    String identifier,

    @NotEmpty(message = "User's FullName cannot be empty")
    @Size(min = 5, max = 50)
    String fullName,

    @NotEmpty(message = "User's address cannot be empty")
    String address,

    @NotEmpty(message = "User's address cannot be empty")
    @Pattern(regexp = "^\\d*$", message = "Phone number should use only digits")
    String phone,

    @NotEmpty
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "wrong email value")
    String email
){
}
