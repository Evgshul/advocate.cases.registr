package com.lv.adv.cass.regstr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    @NotEmpty(message = "User's identifier cannot be empty.")
    @Size(min = 5, max = 12)
    private String identifier;

    private String fullName;

    private String address;

    private String phone;

    private String email;
}
