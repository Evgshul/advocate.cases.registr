package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.model.Persons;
import com.lv.adv.cass.regstr.repository.PersonsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Class for managing on Persons entity
 */
@Service
public class PersonsServiceImpl implements PersonsService {

    private static final Logger log = LoggerFactory.getLogger(PersonsServiceImpl.class);
    private PersonsRepository personsRepository;

    @Autowired
    public PersonsServiceImpl(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    /**
     * Method provide list of all person
     * @return list of persons
     */
    @Override
    public List<Persons> getAllPersons() {
        return personsRepository.findAll();
    }

    /**
     * Method to add new entry in table persons
     * @param person unique entry in entity persons
     */
    @Override
    public void addPerson(Persons person) {
        Optional<Persons> personByIdentifier = personsRepository.findByIdentifier(person.getIdentifier());
        if (personByIdentifier.isPresent()) {
            throw new IllegalStateException("person exist");
        }
        personsRepository.save(person);
        log.debug("new person fullName {}; identifier {}", person.getFullName(), person.getIdentifier());
    }

    /**
     * method removed entry from table persons
     * @param personId unique identifier of entry in entity persons
     */
    @Override
    public void deletePerson(Long personId) {
        if (!personsRepository.existsById(personId)) {
            log.debug("person with id: {} not found", personId);
            throw new IllegalStateException("Person with id " + personId + "not find");
        }
        log.debug("person with id: {} found and will remove", personId);
        personsRepository.deleteById(personId);
    }

    /**
     * Method to update existing entry in entity persons
     * @param personId Id
     * @param identifier identifier
     * @param fullName fullName
     * @param email email
     * @param phone phone
     */
    @Transactional
    @Override
    public void updatePerson(final Long personId,
                             final String identifier,
                             final String fullName,
                             final String email,
                             final String phone
                             ) {
        Persons existPerson = personsRepository.findById(personId)
                .orElseThrow(() -> new IllegalStateException(
                        "Person with id " + personId + " does not exist"
                ));

        if (isNotEmpty(identifier) && !Objects.equals(existPerson.getIdentifier(), identifier)) {
            Optional<Persons> person = personsRepository.findByIdentifier(identifier).stream().findFirst();
            if (person.isPresent()) {
                throw new IllegalStateException("identifier ".concat(identifier).concat(" exist"));
            }
            existPerson.setIdentifier(identifier);
        }
        if (isNotEmpty(fullName) && !Objects.equals(existPerson.getFullName(), fullName)) {
            existPerson.setIdentifier(fullName);
        }
        if (isNotEmpty(email) && !Objects.equals(existPerson.getEmail(), email)) {
            existPerson.setIdentifier(email);
        }
        if (isNotEmpty(phone) && !Objects.equals(existPerson.getPhone(), phone)) {
            existPerson.setIdentifier(phone);
        }
    }
}
