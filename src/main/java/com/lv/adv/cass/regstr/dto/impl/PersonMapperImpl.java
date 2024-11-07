package com.lv.adv.cass.regstr.dto.impl;

import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.dto.mapper.PersonMapper;
import com.lv.adv.cass.regstr.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonMapperImpl implements PersonMapper {

    /**
     * Method helps mapping PersonsDto entity fields values to Persons fields values.
     * @param personDto PersonDto entity with fields values.
     * @return persons entity with required values.
     */
    @Override
    public Person personDtoToPerson(PersonDto personDto) {
        // map person to personDto
        return Person.builder()
                .identifier(personDto.identifier())
                .fullName(personDto.fullName())
                .address(personDto.address())
                .phone(personDto.phone())
                .email(personDto.email())
                .build();
    }

    @Override
    public List<Person> personsDtoToPersons(List<PersonDto> personDto) {
        return personDto.stream().map(person -> Person.builder()
                .identifier(person.identifier())
                .fullName(person.fullName())
                .address(person.address())
                .phone(person.phone())
                .email(person.email())
                .build()).toList();
    }

    @Override
    public List<PersonDto> personsToPersonsDto(List<Person> persons) {
        return persons.stream().map(person -> new PersonDto(person.getIdentifier(),
                person.getFullName(),
                person.getAddress(),
                person.getPhone(),
                person.getEmail())).toList();
    }


    /**
     * Method helps mapping Persons entity fields values to PersonsDto fields values.
     * @param person Person entity with fields values.
     * @return personsDto entity with required values.
     */
    @Override
    public PersonDto personToPersonDto(Person person) {
        return new PersonDto(person.getIdentifier(),
                person.getFullName(),
                person.getAddress(),
                person.getPhone(),
                person.getEmail());
    }
}
