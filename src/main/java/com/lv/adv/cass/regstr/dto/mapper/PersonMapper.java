package com.lv.adv.cass.regstr.dto.mapper;

import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.model.Person;

import java.util.List;


public interface PersonMapper {

    Person personDtoToPerson(PersonDto personDto);

    List<Person> personsDtoToPersons(List<PersonDto> personDto);

    List<PersonDto> personsToPersonsDto(List<Person> person);

    PersonDto personToPersonDto(Person person);
}
