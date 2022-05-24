package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.model.Persons;

import java.util.List;
import java.util.UUID;

public interface PersonsService {

    // get list of all persons
    List<Persons> getAllPersons();

    // method to add new person
    void addPerson(Persons person);

    // method for remove person
    void deletePerson(UUID personId);

    // method to update existing person
    void updatePerson(final UUID personId,
                      final String identifier,
                      final String fullName,
                      final String address,
                      final String email,
                      final String phone);
}
