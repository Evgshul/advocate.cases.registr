package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.dto.CustomerDto;
import com.lv.adv.cass.regstr.dto.mapper.CustomerMapper;
import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.model.Customer;
import com.lv.adv.cass.regstr.repository.CustomersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class CustomersServiceImpl implements CustomersService{

    private static final Logger log = LoggerFactory.getLogger(CustomersServiceImpl.class);

    private final CustomersRepository customersRepository;

    private final CustomerMapper customerMapper;


    @Autowired
    public CustomersServiceImpl(final CustomersRepository customersRepository, CustomerMapper customerMapper) {
        this.customersRepository = customersRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        final List<Customer> customers = customersRepository.findAll();
        return customers.stream().map(this.customerMapper::customerToCustomerDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addNewCustomer(CustomerDto customerDto) {
        Optional<Customer> customerByIdentifier = customersRepository.findByIdentifier(customerDto.getIdentifier());
        if (customerByIdentifier.isPresent()) {
            throw new IllegalStateException("customers exist");
        }

        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
        customersRepository.save(customer);
        log.debug("new customers name {}; identifier {}", customerDto.getCustomerName(), customerDto.getIdentifier());
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        if (!customersRepository.existsById(customerId)) {
            throw new IllegalStateException("customer with id: " + customerId + "is not exist");
        }
        log.debug("customers with id {} found and will remove", customerId);
        customersRepository.deleteById(customerId);
    }

    @Transactional
    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        Customer existCustomer = customersRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException(
                        "can not find customer with id: ".concat(customerId.toString())
                ));

        final String customerIdentifier = customerDto.getIdentifier();
        if (isNotEmpty(customerIdentifier) && !Objects.equals(existCustomer.getIdentifier(), customerIdentifier)) {
            existCustomer.setIdentifier(customerIdentifier);
            log.debug("identifier changed from {} to {}", existCustomer.getIdentifier(), customerIdentifier);
        }

        final String customerName = customerDto.getCustomerName();
        if (isNotEmpty(customerName) && !Objects.equals(existCustomer.getCustomerName(), customerName)) {
            existCustomer.setCustomerName(customerName);
            log.debug("customer name changed from {} to {}", existCustomer.getCustomerName(), customerName);
        }
        final String declaredAddress = customerDto.getDeclaredAddress();
        if (isNotEmpty(declaredAddress) && !Objects.equals(existCustomer.getDeclaredAddress(), declaredAddress)) {
            existCustomer.setDeclaredAddress(declaredAddress);
            log.debug("customer declaredAddress changed from {} to {}", existCustomer.getDeclaredAddress(), declaredAddress);
        }

        final String actualAddress = customerDto.getActualAddress();
        if (isNotEmpty(actualAddress) && !Objects.equals(existCustomer.getActualAddress(), actualAddress)) {
            existCustomer.setActualAddress(actualAddress);
            log.debug("customer actualAddress changed from {} to {}", existCustomer.getActualAddress(), actualAddress);
        }

        final String email = customerDto.getEmail();
        if (isNotEmpty(email) && !Objects.equals(existCustomer.getEmail(), email)) {
            existCustomer.setEmail(email);
            log.debug("customer email changed from {} to {}", existCustomer.getEmail(), email);
        }

        final String phone = customerDto.getPhone();
        if (isNotEmpty(phone) && !Objects.equals(existCustomer.getPhone(), phone)) {
            existCustomer.setPhone(phone);
            log.debug("customer phone changed from {} to {}", existCustomer.getPhone(), phone);
        }
    }

    @Override
    public CustomerDto findCustomerByName(String name) {
        return null;
    }

    @Override
    public List<CustomerDto> findCustomerByPerson(PersonDto personDto) {
        return null;
    }
}
