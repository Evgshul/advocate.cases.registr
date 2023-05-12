package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    // get list of all persons
    List<PersonDto> getPersons();

    // method to add new person
    Person addPerson(PersonDto personsDto);

    // method to find person by fullName
    PersonDto findPersonByFullName(String fullName);

    // method to remove person
    void deletePerson(UUID personId);

    // method to update existing person
    Person updatePerson(final UUID personId, final PersonDto personsDto);
}
