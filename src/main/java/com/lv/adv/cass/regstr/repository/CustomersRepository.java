package com.lv.adv.cass.regstr.repository;

import com.lv.adv.cass.regstr.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByIdentifier(String identifier);
}
