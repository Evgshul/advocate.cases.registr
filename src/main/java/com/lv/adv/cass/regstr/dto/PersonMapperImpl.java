package com.lv.adv.cass.regstr.dto;

import com.lv.adv.cass.regstr.model.Person;
import com.lv.adv.cass.regstr.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonMapperImpl implements PersonMapper {

    private final PersonRepository personRepository;

    private final ModelMapper mapper;

    @Autowired
    public PersonMapperImpl(PersonRepository personRepository, ModelMapper mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    /**
     * Method helps mapping PersonsDto entity fields values to Persons fields values.
     * @param personDto PersonDto entity with fields values.
     * @return persons entity with required values.
     */
    @Override
    public Person personDtoToPerson(PersonDto personDto) {
        return this.mapper.map(personDto, Person.class);
    }

    /**
     * Method helps mapping Persons entity fields values to PersonsDto fields values.
     * @param person Person entity with fields values.
     * @return personsDto entity with required values.
     */
    @Override
    public PersonDto personToPersonDto(Person person) {
        return this.mapper.map(person, PersonDto.class);
    }

//    @Override
//    public List<PersonDto> getPersonsList() {
//        final List<Person> personList = this.personRepository.findAll();
//        return personList.stream().map(person -> mapper.map(person, PersonDto.class)).collect(Collectors.toList());
//    }
}
