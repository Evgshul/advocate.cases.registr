package com.lv.adv.cass.regstr.dto;

import com.lv.adv.cass.regstr.model.Person;

import java.util.List;

public interface PersonMapper {

    Person personDtoToPerson(PersonDto person);

    PersonDto personToPersonDto(Person person);

    List<PersonDto> getPersonsList();
}
