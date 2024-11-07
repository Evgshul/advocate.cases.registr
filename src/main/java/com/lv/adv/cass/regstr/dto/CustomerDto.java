package com.lv.adv.cass.regstr.dto;

import com.lv.adv.cass.regstr.model.Person;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerDto {

    private String isCompany;

    private String companyName;

    private String registrationNumber;

    private PersonDto personDto;

    private String declaredAddress;

    private String actualAddress;

    private String phone;

    private String email;

    private Person representative;
}
