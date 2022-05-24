package com.lv.adv.cass.regstr.repository;

import com.lv.adv.cass.regstr.model.Persons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonsRepository extends JpaRepository<Persons, UUID> {

//    @Query(value = "SELECT * FROM Persons WHERE identifier = :identifier",
//            nativeQuery = true)
    Optional<Persons> findByIdentifier(String identifier);
}
