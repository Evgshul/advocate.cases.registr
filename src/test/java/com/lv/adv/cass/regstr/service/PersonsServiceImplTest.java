package com.lv.adv.cass.regstr.service;


import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.dto.mapper.PersonMapper;
import com.lv.adv.cass.regstr.dto.impl.PersonMapperImpl;
import com.lv.adv.cass.regstr.model.Person;
import com.lv.adv.cass.regstr.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
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


    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        PersonMapper personMapper = new PersonMapperImpl(mapper);
        underTest = new PersonsServiceImpl(personRepository, personMapper);
    }

    @Test
    void savePersonTest_success() {
        PersonDto person = createValidPersonDto();

        underTest.addPerson(person);

        Optional<Person> checkPerson = personRepository.findPersonsByEmail(person.getEmail());
        assertTrue(checkPerson.isPresent(), "Person should exist");
        assertEquals("John Silver", checkPerson.get().getFullName(), "Invalid Person fullName value");
        assertEquals("037127090911", checkPerson.get().getPhone(), "Invalid Person phoneNumber value");
        assertEquals("test@mail.org", checkPerson.get().getEmail(), "Invalid Person email value");

    }

    @Test
    void savePersonTest_FullNameExist() {
        PersonDto person = createValidPersonDto();
        underTest.addPerson(person);

        PersonDto newPerson = new PersonDto();
        newPerson.setIdentifier("IND4567");
        newPerson.setFullName("John Silver");
        newPerson.setEmail("test2@mail.com");
        newPerson.setAddress("Latvia Riga");
        newPerson.setPhone("097256987421");


        Exception exception = assertThrows(IllegalStateException.class,
                () -> underTest.addPerson(newPerson));
        assertTrue(exception.getMessage().contains("Person fullName " + newPerson.getFullName() + " is taken"));
    }

    @Test
    void testSavePerson_EmailExist() {
        PersonDto person = createValidPersonDto();
        underTest.addPerson(person);

        PersonDto newPerson = new PersonDto();
        newPerson.setIdentifier("IND4567");
        newPerson.setFullName("Yangya Satpath");
        newPerson.setAddress("Latvia Riga");
        newPerson.setEmail("test@mail.org");
        newPerson.setPhone("097256987421");


        Exception exception = assertThrows(IllegalStateException.class,
                () -> underTest.addPerson(newPerson));
        assertTrue(exception.getMessage().contains("Email " + newPerson.getEmail() + " is taken"));
    }

    @Test
    void testAddPerson_PhoneNumberExist() {
        PersonDto person = createValidPersonDto();
        underTest.addPerson(person);

        PersonDto newPerson = new PersonDto();
        newPerson.setIdentifier("IND4567");
        newPerson.setFullName("Yangya Satpath");
        newPerson.setAddress("Latvija");
        newPerson.setEmail("test@gmail.com");
        newPerson.setPhone("037127090911");

        Exception exception = assertThrows(IllegalStateException.class,
                () -> underTest.addPerson(newPerson));
        assertTrue(exception.getMessage().contains("Phone " + newPerson.getPhone() + " is taken"));
    }

    @Test
    void testFindPirsonByName_success() {
        Person person = createValidPerson();
        personRepository.save(person);

        PersonDto checkPerson = underTest.findPersonByFullName("John Silver");
        assertNotNull(checkPerson, "Person should exist");
        assertEquals("John Silver", checkPerson.getFullName(), "Invalid Person fullName value");
        assertEquals("037127090911", checkPerson.getPhone(), "Invalid Person phoneNumber value");
        assertEquals("test@mail.org", checkPerson.getEmail(), "Invalid Person email value");

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

        assertTrue(finalPersonList.stream().noneMatch(p -> person2.getFullName().equals(p.getFullName())
                        && person2.getEmail().equals(p.getEmail())),
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


        PersonDto personNewValus = new PersonDto();
        personNewValus.setIdentifier("231209-23111");
        personNewValus.setFullName("Yangya Satpath");
        personNewValus.setAddress("SUper Address");
        personNewValus.setEmail("test2@mail.com");
        personNewValus.setPhone("097256987421");

        final UUID personId = person.getId();
        underTest.updatePerson(personId, personNewValus);
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
        person2.setFullName("Yangya Satpath");
        person2.setEmail("test2@mail.com");
        person2.setPhone("097256987421");
        personRepository.save(person2);


        PersonDto personNewValues = new PersonDto();
        personNewValues.setFullName("Yangya Satpath");
        personNewValues.setEmail("test2@mail.com");
        personNewValues.setPhone("097256987421");

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


        PersonDto personNewValues = new PersonDto();
        personNewValues.setIdentifier("238943-21677");
        personNewValues.setFullName("John Silver");
        personNewValues.setAddress("Poland");
        personNewValues.setEmail("test@mail.org");
        personNewValues.setPhone("037127090911");

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
                "Some Adress",
                "037127090911",
                "test@mail.org");
    }

    private Person createValidPerson() {


        return new Person(
                "150510-45789",
                "John Silver",
                "Some Fine Address",
                "037127090911",
                "test@mail.org");
    }
}