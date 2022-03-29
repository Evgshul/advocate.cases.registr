package com.lv.adv.cass.regstr.dto;

import com.lv.adv.cass.regstr.model.Persons;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomersDto {

    private String identifier;

    private String customerName;

    private String declaredAddress;

    private String actualAddress;

    private String phone;

    private String email;

    private String person;
}
