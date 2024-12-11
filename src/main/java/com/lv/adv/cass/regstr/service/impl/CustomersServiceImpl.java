package com.lv.adv.cass.regstr.service.impl;

import com.lv.adv.cass.regstr.dto.CustomerDto;
import com.lv.adv.cass.regstr.dto.mapper.CustomerMapper;
import com.lv.adv.cass.regstr.model.Customer;
import com.lv.adv.cass.regstr.model.Person;
import com.lv.adv.cass.regstr.repository.CustomersRepository;
import com.lv.adv.cass.regstr.repository.PersonRepository;
import com.lv.adv.cass.regstr.service.CustomersService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.lv.adv.cass.regstr.constants.MessageConstants.IS_NOT_PRESENT;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class CustomersServiceImpl implements CustomersService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomersServiceImpl.class);

    private final CustomersRepository customersRepository;

    private final PersonRepository personRepository;

    private final CustomerMapper customerMapper;


    @Autowired
    public CustomersServiceImpl(final CustomersRepository customersRepository, PersonRepository personRepository, CustomerMapper customerMapper) {
        this.customersRepository = customersRepository;
        this.personRepository = personRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customersRepository.findAll().stream()
                .map(this.customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    @Transactional
    public void addNewCustomer(CustomerDto customerDto) {
        customersRepository.findByRegistrationNumber(customerDto.getRegistrationNumber())
                .ifPresent(customer -> {
                    throw new IllegalStateException("Customer with registration number: "
                            .concat(customerDto.getRegistrationNumber()).concat(IS_NOT_PRESENT));
                });

        customersRepository.findCustomerByCompanyName(customerDto.getCompanyName())
                .ifPresent(customer -> {
                    throw new IllegalStateException(
                            String.format("Customer with company name %s %s", customerDto.getCompanyName(), IS_NOT_PRESENT));
                });

        customersRepository.save(customerMapper.customerDtoToCustomer(customerDto));
        LOG.debug("new customers with name {} and identifier {} registered", customerDto.getCompanyName(), customerDto.getRegistrationNumber());
    }

    @Override
    @Transactional
    public void deleteCustomer(UUID customerId) {
        if (!customersRepository.existsById(customerId)) {
            throw new IllegalStateException("customer with id: " + customerId + "is not exist");
        }
        LOG.debug("customers with id {} found and will remove", customerId);
        customersRepository.deleteById(customerId);
    }

    @Override
    public CustomerDto findByDeclaredAddress(String declaredAddress) {
        return customersRepository.findByDeclaredAddress(declaredAddress)
                .map(customerMapper::customerToCustomerDto)
                .orElseThrow(() -> new IllegalStateException("Declared address: ".concat(declaredAddress).concat(IS_NOT_PRESENT)));
    }

    @Override
    public CustomerDto findByActualAddress(String actualAddress) {
        return customersRepository.findByActualAddress(actualAddress)
                .map(customerMapper::customerToCustomerDto)
                .orElseThrow(() -> new IllegalStateException("Actual address: ".concat(actualAddress).concat(IS_NOT_PRESENT)));
    }

    @Override
    public CustomerDto findByCustomerId(UUID personId) {
        return customersRepository.findByPersonId(personId)
                .map(customerMapper::customerToCustomerDto)
                .orElseThrow(() -> new IllegalStateException(String.format("Customer %s with the %s", IS_NOT_PRESENT, personId.toString())));
    }

    @Override
    public List<CustomerDto> findCustomers(String keyword) {
        if (keyword.isBlank()) {
            return customersRepository.findAll().stream().map(customerMapper::customerToCustomerDto).toList();
        }
        return customersRepository.searchCustomers(keyword.toLowerCase())
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Transactional
    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        Customer existingCustomer = customersRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException(
                        "can not find customer with id: ".concat(customerId.toString())
                ));

        if (ObjectUtils.notEqual(customerDto.getCompanyName(), existingCustomer.getCompanyName())) {
            existingCustomer.setCompanyName(customerDto.getCompanyName());
        }

        if (customerDto.getRegistrationNumber() != null &&
                !customerDto.getRegistrationNumber().equals(existingCustomer.getRegistrationNumber())) {
            existingCustomer.setRegistrationNumber(customerDto.getRegistrationNumber());
        }

        if (customerDto.getDeclaredAddress() != null &&
                !customerDto.getDeclaredAddress().equals(existingCustomer.getDeclaredAddress())) {
            existingCustomer.setDeclaredAddress(customerDto.getDeclaredAddress());
        }

        if (customerDto.getActualAddress() != null &&
                !customerDto.getActualAddress().equals(existingCustomer.getActualAddress())) {
            existingCustomer.setActualAddress(customerDto.getActualAddress());
        }

        if (customerDto.getPhone() != null &&
                !customerDto.getPhone().equals(existingCustomer.getPhone())) {
            existingCustomer.setPhone(customerDto.getPhone());
        }

        if (customerDto.getEmail() != null &&
                !customerDto.getEmail().equals(existingCustomer.getEmail())) {
            existingCustomer.setEmail(customerDto.getEmail());
        }
        // Check and update Person
        updatePerson(existingCustomer, customerDto.getPersonIdentifier());

        // Check and update Representative
        updateRepresentative(existingCustomer, customerDto.getRepresentativeIdentifier());

        customersRepository.save(existingCustomer);
    }

    private void updatePerson(Customer customer, String personIdentifier) {
        if (isNotBlank(personIdentifier)) {
            Person newPerson = personRepository.findByIdentifier(personIdentifier)
                    .orElseThrow(() -> new IllegalArgumentException("Person not found"));

            if (ObjectUtils.notEqual(customer.getPerson(), newPerson)) {
                customer.setPerson(newPerson);
            }
        }
    }

    private void updateRepresentative(Customer customer, String representativeIdentifier) {
        if (isNotBlank(representativeIdentifier)) {
            Person newRepresentative = personRepository.findByIdentifier(representativeIdentifier)
                    .orElseThrow(() -> new IllegalArgumentException("Representative not found"));

            if (ObjectUtils.notEqual(customer.getRepresentative(), newRepresentative)) {
                customer.setRepresentative(newRepresentative);
            }
        }
    }
}
