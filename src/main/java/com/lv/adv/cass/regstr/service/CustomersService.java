package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.dto.CustomersDto;
import com.lv.adv.cass.regstr.model.Customers;
import com.lv.adv.cass.regstr.model.Persons;

import java.util.List;
import java.util.UUID;

public interface CustomersService {

    List<Customers> allCustomers();
    void addCustomers(Customers customer);
    void deleteCustomers(UUID customerId);
    void updateCustomers(UUID id,
                         Persons person,
                         String identifier,
                         String name,
                         String declaredAddress,
                         String actualAddress,
                         String email,
                         String phone);
}
