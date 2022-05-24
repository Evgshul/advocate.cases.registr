package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.model.Persons;
import com.lv.adv.cass.regstr.repository.PersonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PersonServiceTest {

    @Autowired
    private PersonsRepository underTest;

    private PersonsServiceImpl personsService;

//    private Persons persons;

    @BeforeEach
    public void setup() {
        personsService = new PersonsServiceImpl(underTest);
    }
    
    @Test
    public void testCreateNewPerson() {

        Persons person = createPerson();
        personsService.addPerson(person);

        Optional<Persons> optionalPerson = underTest.findByIdentifier(person.getIdentifier());

        assertThat(optionalPerson)
                .isPresent().hasValueSatisfying(p -> {
                    assertThat(p).isEqualToComparingFieldByField(person);
                });
    }

    @Test
    public void testCreateNewPerson_identifierTheSame() {
        Persons person = createPerson();
        underTest.save(person);

        Persons persons1 = new Persons();
        persons1.setIdentifier("ind1");
        persons1.setFullName("Anda Jansone");
        persons1.setAddress("any street");
        persons1.setEmail("bezmail@gmail.tr");
        persons1.setPhone("+375 5678911");

        Exception exception = assertThrows(IllegalStateException.class,
                () -> personsService.addPerson(persons1));
        assertTrue(exception.getMessage().contains("person exist"));

    }

    @Test
    public void testDeletePerson_Success() {
        Persons person = createPerson();
        underTest.save(person);

        assertThat(person).isNotNull();

        UUID id = person.getId();
        personsService.deletePerson(id);
        assertTrue(underTest.findById(id).isEmpty());
    }

    @Test
    public void testDeletePerson_IdNotExist() {
        Persons person = createPerson();
        underTest.save(person);

        assertThat(person).isNotNull();
        final UUID personId = UUID.randomUUID();

        Exception exception = assertThrows(IllegalStateException.class,
                () -> personsService.deletePerson(personId));
        assertTrue(exception.getMessage().contains("Person with id " + personId + " not find"));
    }

    private Persons createPerson() {
        return new Persons(
                "Jack Dale",
                "ind1",
                "Big deal avenue",
                "+371 85469777",
                "email@inbox.ll");
    }

}
