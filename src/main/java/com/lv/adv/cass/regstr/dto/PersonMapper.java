package com.lv.adv.cass.regstr.dto;

import com.lv.adv.cass.regstr.model.Person;


public interface PersonMapper {

    Person personDtoToPerson(PersonDto person);

    PersonDto personToPersonDto(Person person);

}
