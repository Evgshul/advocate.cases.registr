package com.lv.adv.cass.regstr.service;


import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.dto.mapper.PersonMapper;
import com.lv.adv.cass.regstr.dto.impl.PersonMapperImpl;
import com.lv.adv.cass.regstr.model.Person;
import com.lv.adv.cass.regstr.repository.PersonRepository;
import com.lv.adv.cass.regstr.service.impl.PersonsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class PersonsServiceImplTest {

    @Autowired
    private PersonRepository personRepository;

    private PersonService underTest;


    @BeforeEach
    void setUp() {
        PersonMapper personMapper = new PersonMapperImpl();
        underTest = new PersonsServiceImpl(personRepository, personMapper);
    }

    @Test
    void savePersonTest_success() {
        PersonDto person = createValidPersonDto();

        underTest.addPerson(person);

        Optional<Person> checkPerson = personRepository.findPersonsByEmail(person.email());
        assertTrue(checkPerson.isPresent(), "Person should exist");
        assertEquals("John Silver", checkPerson.get().getFullName(), "Invalid Person fullName value");
        assertEquals("037127090911", checkPerson.get().getPhone(), "Invalid Person phoneNumber value");
        assertEquals("test@mail.org", checkPerson.get().getEmail(), "Invalid Person email value");

    }

    @Test
    void savePersonTest_FullNameExist() {
        PersonDto person = createValidPersonDto();
        underTest.addPerson(person);

        PersonDto newPerson = new PersonDto("IND4567",
                "John Silver",
                "Latvia Riga",
                "097256987421",
                "test2@mail.com");

        Exception exception = assertThrows(IllegalStateException.class,
                () -> underTest.addPerson(newPerson));
        assertTrue(exception.getMessage().contains("Person fullName " + newPerson.fullName() + " is taken"));
    }

    @Test
    void testSavePerson_EmailExist() {
        PersonDto person = createValidPersonDto();
        underTest.addPerson(person);

        PersonDto newPerson = new PersonDto(
                "IND4567",
                "Yangya Satpath",
                "Latvia Riga",
                "097256987421",
                "test@mail.org");

        Exception exception = assertThrows(IllegalStateException.class,
                () -> underTest.addPerson(newPerson));
        assertTrue(exception.getMessage().contains("Email " + newPerson.email() + " is taken"));
    }

    @Test
    void testFindPersonByName_success() {
        Person person = createValidPerson();
        personRepository.save(person);

        PersonDto checkPerson = underTest.findPersonByFullName("John Silver");
        assertNotNull(checkPerson, "Person should exist");
        assertEquals("John Silver", checkPerson.fullName(), "Invalid Person fullName value");
        assertEquals("037127090911", checkPerson.phone(), "Invalid Person phoneNumber value");
        assertEquals("test@mail.org", checkPerson.email(), "Invalid Person email value");
    }

    @Test
    void testFindPirsonByName_FullNameNotExist() {
        Person person = createValidPerson();
        personRepository.save(person);

        Exception exception = assertThrows(IllegalStateException.class,
                () -> underTest.findPersonByFullName("Den Philips"));
        assertTrue(exception.getMessage().contains("Fullname Den Philips is not present"));
    }

    @Test
    void testDeletePerson_success() {
        Person person1 = createValidPerson();
        personRepository.save(person1);

        Person person2 = new Person();
        person2.setIdentifier("321243-2345");
        person2.setFullName("Yangya Satpath");
        person2.setAddress("Irland");
        person2.setEmail("test2@mail.com");
        person2.setPhone("097256987421");
        personRepository.save(person2);

        final List<PersonDto> createdPersons = underTest.getAllPersons();
        assertEquals(2, createdPersons.size(), "Should be two Persons created");

        underTest.deletePerson(person2.getId());
        final List<PersonDto> finalPersonList = underTest.getAllPersons();
        assertEquals(1, finalPersonList.size(), "Should be only one Persons");

        assertTrue(finalPersonList.stream().noneMatch(p -> person2.getFullName().equals(p.fullName())
                        && person2.getEmail().equals(p.email())),
                "Person with name 'Yangya Satpath' should not exist");
    }

    @Test
    void testDeletePerson_SuchPersonIsNotExist() {
        Person person = createValidPerson();
        personRepository.save(person);
        UUID personId = UUID.randomUUID();

        Exception exception = assertThrows(IllegalStateException.class,
                () -> underTest.deletePerson(personId));
        assertTrue(exception.getMessage().contains("Person with ID " + personId + " not found"));
    }

    @Test
    void testUpdatePerson_success() {
        Person person = createValidPerson();
        personRepository.save(person);


        PersonDto personNewValues = new PersonDto(
                "231209-23111",
                "Yangya Satpath",
                "SUper Address",
                "097256987421",
                "test2@mail.com"
        );

        final UUID personId = person.getId();
        underTest.updatePerson(personId, personNewValues);
        Optional<Person> updatedPerson = personRepository.findById(personId);
        assertTrue(updatedPerson.isPresent(), "Person should exist");

        assertEquals("Yangya Satpath", updatedPerson.get().getFullName(), "Invalid Person fullName value");
        assertEquals("097256987421", updatedPerson.get().getPhone(), "Invalid Person phoneNumber value");
        assertEquals("test2@mail.com", updatedPerson.get().getEmail(), "Invalid Person email value");

    }

    @Test
    void testUpdatePerson_InvalidParametrsToUpdate_1() {
        Person person = createValidPerson();
        personRepository.save(person);

        Person person2 = new Person();
        person2.setIdentifier("2345612-1234");
        person2.setFullName("Yangya Satpath");
        person2.setAddress("Some Fine Address");
        person2.setEmail("test2@mail.com");
        person2.setPhone("097256987421");
        personRepository.save(person2);


        PersonDto personNewValues = new PersonDto(
                "2345612-1234",
                "Yangya Satpath",
                "Some Fine Address", "097256987421", "test2@mail.com\"");

        final UUID personId = person2.getId();
        underTest.updatePerson(personId, personNewValues);
        Optional<Person> updatedPerson = personRepository.findById(personId);
        assertTrue(updatedPerson.isPresent(), "Person should exist");

        assertEquals(person2, updatedPerson.get(), "The 'Person2' entity should not be changed if the parameters for update equal existing params");
    }

    @Test
    void testUpdatePerson_InvalidParametrsToUpdate_2() {
        Person person = createValidPerson();
        personRepository.save(person);

        Person person2 = new Person();
        person2.setIdentifier("160899-72345");
        person2.setFullName("Yangya Satpath");
        person2.setAddress("Albania");
        person2.setEmail("test2@mail.com");
        person2.setPhone("097256987421");
        personRepository.save(person2);


        PersonDto personNewValues = new PersonDto("238943-21677",
                "John Silver",
                "Poland",
                "test@mail.org",
                "037127090911"
        );

        final UUID personId = person2.getId();
        underTest.updatePerson(personId, personNewValues);
        Optional<Person> updatedPerson = personRepository.findById(personId);
        assertTrue(updatedPerson.isPresent(), "Person should exist");

        assertEquals(person2, updatedPerson.get(), "The 'Person2' entity should not be changed if the parameters was taken");
    }

    private PersonDto createValidPersonDto() {
        return new PersonDto(
                "150510-45789",
                "John Silver",
                "Some Address",
                "037127090911",
                "test@mail.org");
    }

    private Person createValidPerson() {


        return Person.builder()
                .identifier("150510-45789")
                .fullName("John Silver")
                .address("Some Fine Address")
                .phone("037127090911")
                .email("test@mail.org")
                .build();
    }
}