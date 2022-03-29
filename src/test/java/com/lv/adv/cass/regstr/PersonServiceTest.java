package com.lv.adv.cass.regstr;

import com.lv.adv.cass.regstr.model.Persons;
import com.lv.adv.cass.regstr.repository.PersonsRepository;
import com.lv.adv.cass.regstr.service.PersonsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PersonServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonsRepository personsRepository;

    private PersonsServiceImpl personsService;

    private Persons persons;

    @BeforeEach
    public void setup() {
        personsService = new PersonsServiceImpl(personsRepository);
    }
    
    @Test
    public void testCreateNewPerson() {
        persons = createPerson();
        personsService.addPerson(persons);
        List<Persons> persons = personsService.getAllPersons();

        assertTrue(persons.stream().anyMatch(person ->
                "Jack Dale".equals(person.getFullName())
                        && "ind1".equals(person.getIdentifier())
                        && "+371 85469777".equals(person.getPhone())
                        && "email@inbox.ll".equals(person.getEmail())));
    }

    @Test
    public void testCreateNewPerson_identifierTheSame() {
        persons = createPerson();
        personsService.addPerson(persons);

        Persons persons1 = new Persons();
        persons1.setIdentifier("ind1");
        persons1.setFullName("Anda Jansone");
        persons1.setEmail("bezmail@gmail.tr");
        persons1.setPhone("+375 5678911");

        Exception exception = assertThrows(IllegalStateException.class, () -> personsService.addPerson(persons1));
        assertTrue(exception.getMessage().contains("person exist"));

    }

    @Test
    public void testDeletePerson_Success() {
        persons = createPerson();
        personsService.addPerson(persons);

        assertThat(persons).isNotNull();

        personsService.deletePerson(persons.getId());
        List<Persons> personsList = personsService.getAllPersons();
        assertEquals(0, personsList.size());
    }

    @Test
    public void testDeletePerson_IdNotExist() {
        persons = createPerson();
        personsService.addPerson(persons);

        assertThat(persons).isNotNull();
        final Long personId = 22L;

        Exception exception = assertThrows(IllegalStateException.class,
                () -> personsService.deletePerson(personId));
        assertTrue(exception.getMessage().contains("Person with id " + personId + "not find"));
    }



    private Persons createPerson() {
        Persons person = new Persons();
        person.setIdentifier("ind1");
        person.setFullName("Jack Dale");
        person.setEmail("email@inbox.ll");
        person.setPhone("+371 85469777");

        return person;
    }

}
