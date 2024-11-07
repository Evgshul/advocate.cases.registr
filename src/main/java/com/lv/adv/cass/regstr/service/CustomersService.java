package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.dto.CustomerDto;
import com.lv.adv.cass.regstr.dto.PersonDto;

import java.util.List;
import java.util.UUID;

public interface CustomersService {

    List<CustomerDto> getAllCustomers();

    void addNewCustomer(CustomerDto customer);

    void deleteCustomer(UUID customerId);

    void updateCustomer(UUID customerId, CustomerDto customerDto);

    CustomerDto findCustomerByName(String name);

    CustomerDto findCustomerByRegistrationNumber(String registrationNumber);

    List<CustomerDto> findCustomerByPerson(PersonDto personDto);
}
