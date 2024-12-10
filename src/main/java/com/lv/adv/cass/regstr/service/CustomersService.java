package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.dto.CustomerDto;
import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomersService {

    List<CustomerDto> getAllCustomers();

    void addNewCustomer(CustomerDto customer);

    void deleteCustomer(UUID customerId);

    Customer updateCustomer(UUID customerId, CustomerDto customerDto);

    CustomerDto findByDeclaredAddress(String declaredAddress);
    CustomerDto findByActualAddress(String actualAddress);
    CustomerDto findByPerson(UUID personId);
    List<CustomerDto> findCustomers(String companyName, String registrationNumber, String phone, String email, String personIdentifier, String representativeIdentifier);

}
