package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.model.Customers;

import java.util.List;

public interface CustomersService {

    List<Customers> allCustomers();
    void addCustomers(Customers customer);
    void deleteCustomers(Long customerId);
    void updateCustomers(Long customerId,
                         String identifier,
                         String name,
                         String declaredAddress,
                         String actualAddress,
                         String email,
                         String phone);
}
