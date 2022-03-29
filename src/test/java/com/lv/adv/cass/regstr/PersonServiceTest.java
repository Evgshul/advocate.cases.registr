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
    public void init() {
        personsService = new PersonsServiceImpl(personsRepository);
    }
    
    @Test
    public void testCreateNewPerson() {
        persons = new Persons();
        persons.setIdentifier("ind");
        persons.setFullName("name1");
        persons.setEmail("email1");
        persons.setPhone("phone1");

        personsService.addPerson(persons);

        List<Persons> persons = personsService.getAllPersons();

        assertTrue(persons.stream().anyMatch(person ->
                "name1".equals(person.getFullName())
                        && "ind".equals(person.getIdentifier())
                        && "phone1".equals(person.getPhone())
                        && "email1".equals(person.getEmail())));

    }
}
