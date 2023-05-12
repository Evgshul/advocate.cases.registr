package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.dto.PersonMapper;
import com.lv.adv.cass.regstr.model.Person;
import com.lv.adv.cass.regstr.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Class for managing on Persons entity
 */
@Service
public class PersonsServiceImpl implements PersonService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonsServiceImpl.class);
    private final PersonRepository personRepository;

    private final PersonMapper personsMapper;

    @Autowired
    public PersonsServiceImpl(PersonRepository personRepository,
                              PersonMapper personsMapper) {
        this.personRepository = personRepository;
        this.personsMapper = personsMapper;
    }

    /**
     * Method provide list of all person
     *
     * @return list of persons
     */
    @Override
    public List<PersonDto> getPersons() {
        return personsMapper.getPersonsList();
    }

    /**
     * Method to add new entry in table persons
     */
    @Override
    public Person addPerson(PersonDto personsDto) {
        if (isIndentifierExist(personsDto.getIdentifier())) {
            throw new IllegalStateException("person Identifier " + personsDto.getIdentifier() + " is taken");
        }

        if (isFullnameExist(personsDto.getFullName())) {
            throw new IllegalStateException("Person fullName " + personsDto.getFullName() + " is taken");
        }

        if (isEmailExist(personsDto.getEmail())) {
            throw new IllegalStateException("Email " + personsDto.getEmail() + " is taken");
        }

        UUID id = UUID.randomUUID();
        Person person = this.personsMapper.personDtoToPerson(personsDto);
        person.setId(id);
        LOG.debug("new person fullName {} and identifier {} was created", personsDto.getFullName(), personsDto.getIdentifier());
        return personRepository.save(person);
    }

    @Override
    public PersonDto findPersonByFullName(String fullName) {
        LOG.debug("Searching for person fullName: {}", fullName);
        Optional<Person> personByFullName = personRepository.findByFullName(fullName);
        if (personByFullName.isEmpty()) {
            throw new IllegalStateException("Fullname " + fullName + " is not present");
        } else {
            LOG.debug("Person {} found", fullName);
            return personsMapper.personToPersonDto(personByFullName.get());
        }
    }

    /**
     * method removed entry from table persons
     *
     * @param personId unique identifier of entry in entity persons
     */
    @Override
    @Transactional
    public void deletePerson(UUID personId) {
        if (personRepository.findById(personId).isPresent()) {
            personRepository.deleteById(personId);
            LOG.debug("person with id: {} found and removed", personId);
        } else {
            LOG.debug("person with id: {} not found", personId);
            throw new IllegalStateException("Person with id " + personId + " not find");
        }
    }

    /**
     * Method to update existing entry in entity persons
     *
     * @param personId Id
     */
    @Transactional
    @Override
    public Person updatePerson(final UUID personId, final PersonDto personsDto) {
        Person existPerson = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalStateException(
                        "Person with id " + personId + " does not exist"
                ));

        final String identifier = personsDto.getIdentifier();
        if (!Objects.equals(existPerson.getIdentifier(), identifier) && !isIndentifierExist(identifier)) {
            Optional<Person> person = personRepository.findByIdentifier(identifier).stream().findFirst();
            if (person.isPresent()) {
                throw new IllegalStateException("identifier ".concat(identifier).concat(" exist"));
            }
            existPerson.setIdentifier(identifier);
        }

        final String fullName = personsDto.getFullName();
        if (!Objects.equals(existPerson.getFullName(), fullName) && isFullnameExist(fullName)) {
            existPerson.setFullName(fullName);
        }

        final String address = personsDto.getAddress();
        if (isNotEmpty(address) && !Objects.equals(existPerson.getAddress(), address)) {
            existPerson.setAddress(address);
        }

        final String email = personsDto.getEmail();
        if (!Objects.equals(existPerson.getEmail(), email) && isEmailExist(email)) {
            existPerson.setEmail(email);
        }

        final String phone = personsDto.getPhone();
        if (isNotEmpty(phone) && !Objects.equals(existPerson.getPhone(), phone)) {
            existPerson.setPhone(phone);
        }
        return existPerson;
    }

    private boolean isIndentifierExist(String identifier) {
        return personRepository.findByIdentifier(identifier).isPresent();
    }

    private boolean isFullnameExist(String fullName) {
        return personRepository.findByFullName(fullName).isPresent();
    }

    private boolean isEmailExist(String email) {
        return personRepository.findPersonsByEmail(email).isPresent();
    }
}
