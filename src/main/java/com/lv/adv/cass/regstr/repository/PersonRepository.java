package com.lv.adv.cass.regstr.repository;

import com.lv.adv.cass.regstr.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByIdentifier(String identifier);

    Optional<Person> findPersonsByEmail(String email);

    Optional<Person> findPersonsByPhone(String email);

    Optional<Person> findByFullName(String fullName);
}
