package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.model.Customers;
import com.lv.adv.cass.regstr.model.Persons;
import com.lv.adv.cass.regstr.repository.CustomersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class CustomersServiceImpl implements CustomersService{

    private static final Logger log = LoggerFactory.getLogger(CustomersServiceImpl.class);
    private CustomersRepository customersRepository;

    @Autowired
    public CustomersServiceImpl(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public List<Customers> allCustomers() {
        return customersRepository.findAll();
    }

    @Override
    @Transactional
    public void addCustomers(Customers customer) {
        Optional<Customers> customerByIdentifier = customersRepository.findByIdentifier(customer.getIdentifier());
        if (customerByIdentifier.isPresent()) {
            throw new IllegalStateException("customers exist");
        }
        customersRepository.save(customer);
        log.debug("new customers name {}; identifier {}", customer.getCustomerName(), customer.getIdentifier());
    }

    @Override
    public void deleteCustomers(UUID customerId) {
        if (!customersRepository.existsById(customerId)) {
            throw new IllegalStateException("customer with id: " + customerId + "is not exist");
        }
        log.debug("customers with id {} found and will remove", customerId);
        customersRepository.deleteById(customerId);
    }

    @Transactional
    @Override
    public void updateCustomers(UUID customerId,
                                Persons persons,
                                String identifier,
                                String name,
                                String declaredAddress,
                                String actualAddress,
                                String email,
                                String phone) {
        Customers existCustomer = customersRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException(
                        "can not find customer with id: ".concat(customerId.toString())
                ));
        if (persons != null) {

        }

        if (isNotEmpty(identifier) && !Objects.equals(existCustomer.getIdentifier(), identifier)) {
            existCustomer.setIdentifier(identifier);
            log.debug("identifier changed from {} to {}", existCustomer.getIdentifier(), identifier);
        }

        if (isNotEmpty(name) && !Objects.equals(existCustomer.getCustomerName(), name)) {
            existCustomer.setCustomerName(name);
            log.debug("customer name changed from {} to {}", existCustomer.getCustomerName(), name);
        }
        if (isNotEmpty(declaredAddress) && !Objects.equals(existCustomer.getDeclaredAddress(), declaredAddress)) {
            existCustomer.setDeclaredAddress(declaredAddress);
            log.debug("customer declaredAddress changed from {} to {}", existCustomer.getDeclaredAddress(), declaredAddress);
        }
        if (isNotEmpty(actualAddress) && !Objects.equals(existCustomer.getActualAddress(), actualAddress)) {
            existCustomer.setActualAddress(actualAddress);
            log.debug("customer actualAddress changed from {} to {}", existCustomer.getActualAddress(), actualAddress);
        }
        if (isNotEmpty(email) && !Objects.equals(existCustomer.getEmail(), email)) {
            existCustomer.setEmail(email);
            log.debug("customer email changed from {} to {}", existCustomer.getEmail(), email);
        }
        if (isNotEmpty(phone) && !Objects.equals(existCustomer.getPhone(), phone)) {
            existCustomer.setPhone(phone);
            log.debug("customer phone changed from {} to {}", existCustomer.getPhone(), phone);
        }
    }
}
