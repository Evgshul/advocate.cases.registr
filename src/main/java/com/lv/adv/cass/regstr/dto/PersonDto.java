package com.lv.adv.cass.regstr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    @NotEmpty(message = "Person's identifier cannot be empty.")
    @Size(min = 5, max = 12, message = "Incorrect identifier length, should from 5 to 12 characters")
    private String identifier;

    @NotEmpty(message = "User's FullName cannot be empty")
    @Size(min = 5, max = 50)
    private String fullName;

    @NotEmpty(message = "User's address cannot be empty")
    private String address;

    @NotEmpty(message = "User's address cannot be empty")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number should use only digits")
    private String phone;

    @NotEmpty
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "wrong email value")
    private String email;
}
