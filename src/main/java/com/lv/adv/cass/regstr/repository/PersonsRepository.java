package com.lv.adv.cass.regstr.repository;

import com.lv.adv.cass.regstr.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonsRepository extends JpaRepository<Persons, Long> {

    @Query("SELECT p FROM Persons p WHERE p.identifier = ?1")
    Optional<Persons> findByIdentifier(String identifier);
}
