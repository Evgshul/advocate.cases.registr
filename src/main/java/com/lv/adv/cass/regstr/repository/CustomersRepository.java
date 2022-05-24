package com.lv.adv.cass.regstr.repository;

import com.lv.adv.cass.regstr.model.Customers;
import com.lv.adv.cass.regstr.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, UUID> {

    @Query("SELECT c FROM Customers c WHERE c.identifier = ?1")
    Optional<Customers> findByIdentifier(String identifier);
}
