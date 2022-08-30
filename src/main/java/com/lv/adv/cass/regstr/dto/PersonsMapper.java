package com.lv.adv.cass.regstr.dto;

import com.lv.adv.cass.regstr.model.Persons;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonsMapper {

    @Autowired
    private ModelMapper mapper;

    /**
     * Method helps mapping Persons entity fields values to PersonsDto fields values.
     * @param person Person entity with fields values.
     * @return personsDto entity with required values.
     */
    public PersonsDto mapPersonsToPersonsDto(final Persons person) {
        return mapper.map(person, PersonsDto.class);
    }

    /**
     * Method helps mapping PersonsDto entity fields values to Persons fields values.
     * @param personsDto PersonDto entity with fields values.
     * @return persons entity with required values.
     */
    public Persons mapPersonsDtoToPersons(final PersonsDto personsDto) {
        return mapper.map(personsDto, Persons.class);
    }
}
