package com.lv.adv.cass.regstr.service.impl;

import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.dto.mapper.PersonMapper;
import com.lv.adv.cass.regstr.model.Person;
import com.lv.adv.cass.regstr.repository.PersonRepository;
import com.lv.adv.cass.regstr.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.lv.adv.cass.regstr.constants.MessageConstants.IS_NOT_PRESENT;

/**
 * Class for managing on Persons entity
 */
@Service
public class PersonsServiceImpl implements PersonService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonsServiceImpl.class);
    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Autowired
    public PersonsServiceImpl(PersonRepository personRepository,
                              PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    /**
     * Method provide a list of all persons
     *
     * @return list of persons
     */
    @Override
    public List<PersonDto> getAllPersons() {
        return personRepository.findAll().stream().map(personMapper::personToPersonDto).toList();
    }

    /**
     * Method to add new entry in table persons
     */
    @Override
    @Transactional
    public void addPerson(PersonDto personsDto) {

        if (isIdentifierExist(personsDto.identifier())) {
            throw new IllegalStateException(constructExceptionMessage("person Identifier ",personsDto.identifier()));
        }

        if (isFullnameExist(personsDto.fullName())) {
            throw new IllegalStateException(constructExceptionMessage("Person fullName ", personsDto.fullName()));
        }

        if (isEmailExist(personsDto.email())) {
            throw new IllegalStateException(constructExceptionMessage("Email ", personsDto.email()));
        }
        personRepository.save(personMapper.personDtoToPerson(personsDto));
    }

    @Override
    public PersonDto findPersonByFullName(String fullName) {
        return personRepository.findByFullName(fullName)
                .map(personMapper::personToPersonDto)
                .orElseThrow(() -> new IllegalStateException("Fullname ".concat(fullName).concat(IS_NOT_PRESENT)));
    }

    @Override
    public PersonDto findPersonByEmail(String email) {
        return personRepository.findPersonsByEmail(email)
                .map(personMapper::personToPersonDto).orElseThrow(
                        () -> new IllegalStateException("Email ".concat(email).concat(IS_NOT_PRESENT))
                );
    }

    @Override
    public PersonDto findPersonByPhone(String phone) {
        return personRepository.findPersonsByPhone(phone)
                .map(personMapper::personToPersonDto).orElseThrow(
                        () -> new IllegalStateException("Phone ".concat(phone).concat(IS_NOT_PRESENT))
                );
    }

    /**
     * method removed entry from table persons
     *
     * @param personId unique identifier of entry in entity persons
     */
    @Override
    @Transactional
    public void deletePerson(UUID personId) {
        personRepository.findById(personId)
                .ifPresentOrElse(person -> personRepository.deleteById(personId),
                        () -> {
                            LOG.debug("person with id: {} not found", personId);
                            throw new IllegalStateException("Person with ID " + personId + " not found");
                        }
                );
    }

    /**
     * Method to update existing entry in entity persons
     *
     * @param personId Id
     */
    @Override
    @Transactional
    public Person updatePerson(final UUID personId, final PersonDto personsDto) {
        Person existPerson = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalStateException(
                        "Person with id " + personId + IS_NOT_PRESENT
                ));

        final String identifier = personsDto.identifier();
        if (!Objects.equals(existPerson.getIdentifier(), identifier) && !isIdentifierExist(identifier)) {
            personRepository.findByIdentifier(identifier).stream().findFirst().ifPresent(
                    person -> {
                        throw new IllegalStateException("identifier ".concat(identifier).concat(" exist"));
                    }
            );
            existPerson.setIdentifier(identifier);
            LOG.debug("Person identifier updated to {}", identifier);
        }

        final String fullName = personsDto.fullName();
        if (!Objects.equals(existPerson.getFullName(), fullName) && !isFullnameExist(fullName)) {
            existPerson.setFullName(fullName);
            LOG.debug("Person fullName updated to {}", fullName);
        }

        final String address = personsDto.address();
        if (!Objects.equals(existPerson.getAddress(), address)) {
            LOG.debug("Person address updated to {}", address);
            existPerson.setAddress(address);
        }

        final String email = personsDto.email();
        if (!Objects.equals(existPerson.getEmail(), email) && !isEmailExist(email)) {
            LOG.debug("Person email updated to {}", email);
            existPerson.setEmail(email);
        }

        final String phone = personsDto.phone();
        if (!Objects.equals(existPerson.getPhone(), phone)) {
            existPerson.setPhone(phone);
            LOG.debug("Person phone updated to {}", phone);
        }
        personRepository.save(existPerson);
        return existPerson;
    }

    private boolean isIdentifierExist(String identifier) {
        return personRepository.findByIdentifier(identifier).isPresent();
    }

    private boolean isFullnameExist(String fullName) {
        return personRepository.findByFullName(fullName).isPresent();
    }

    private boolean isEmailExist(String email) {
        return personRepository.findPersonsByEmail(email).isPresent();
    }

    private boolean isPhoneNumberExist(String phone) {
        return personRepository.findPersonsByPhone(phone).isPresent();
    }

    private String constructExceptionMessage(String message, String value) {
        return message.concat(value).concat(" is taken");
    }
}
