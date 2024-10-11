package com.lv.adv.cass.regstr.dto.impl;

import com.lv.adv.cass.regstr.dto.CustomerDto;
import com.lv.adv.cass.regstr.dto.mapper.CustomerMapper;
import com.lv.adv.cass.regstr.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapperImpl implements CustomerMapper {

    private final ModelMapper mapper;

    @Autowired
    public CustomerMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public Customer customerDtoToCustomer(CustomerDto customerDto) {
        return this.mapper.map(customerDto, Customer.class);
    }

    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        return this.mapper.map(customer, CustomerDto.class);
    }
}
