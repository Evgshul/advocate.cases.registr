package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.model.Customer;
import com.lv.adv.cass.regstr.model.Person;

import java.util.List;
import java.util.UUID;

public interface CustomersService {

    List<Customer> allCustomers();
    void addCustomers(Customer customer);
    void deleteCustomers(UUID customerId);
    void updateCustomers(UUID id,
                         Person person,
                         String identifier,
                         String name,
                         String declaredAddress,
                         String actualAddress,
                         String email,
                         String phone);
}
