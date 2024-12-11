package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomersService {

    List<CustomerDto> getAllCustomers();

    void addNewCustomer(CustomerDto customer);

    void deleteCustomer(UUID customerId);

    void updateCustomer(UUID customerId, CustomerDto customerDto);
    CustomerDto findByDeclaredAddress(String declaredAddress);
    CustomerDto findByActualAddress(String actualAddress);
    CustomerDto findByCustomerId(UUID personId);
    List<CustomerDto> findCustomers(String keyword);

}
