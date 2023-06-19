package com.lv.adv.cass.regstr.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerDto {

    private String identifier;

    private String customerName;

    private String declaredAddress;

    private String actualAddress;

    private String phone;

    private String email;

    private String person;
}
