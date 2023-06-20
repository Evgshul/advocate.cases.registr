package com.lv.adv.cass.regstr.dto;

import com.lv.adv.cass.regstr.model.Customer;

public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto customerDto);

    CustomerDto customerToCustomerDto(Customer customer);
}
