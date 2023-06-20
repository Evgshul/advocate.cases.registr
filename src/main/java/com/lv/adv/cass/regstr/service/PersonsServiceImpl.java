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
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

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
     * Method provide list of all person
     *
     * @return list of persons
     */
    @Override
    public List<PersonDto> getAllPersons() {
        final List<Person> personList = this.personRepository.findAll();
        return personList.stream().map(this.personMapper::personToPersonDto).collect(Collectors.toList());
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

        if (isPhoneBumberExist(personsDto.getPhone())) {
            throw new IllegalStateException("Phone " + personsDto.getPhone() + " is taken");
        }

        Person person = this.personMapper.personDtoToPerson(personsDto);
//        LOG.debug("new person fullName {} and identifier {} was created", personsDto.getFullName(), personsDto.getIdentifier());
        return personRepository.save(person);
    }

    @Override
    public PersonDto findPersonByFullName(String fullName) {
        LOG.debug("Searching for person fullName: {}", fullName);
        Optional<Person> personByFullName = personRepository.findByFullName(fullName);
        if (personByFullName.isEmpty()) {
            throw new IllegalStateException(String.format("Fullname %s is not present", fullName));
        } else {
            LOG.debug("Person {} found", fullName);
            return personMapper.personToPersonDto(personByFullName.get());
        }
    }

    @Override
    public PersonDto findPersonByEmail(String email) {
        Optional<Person> personByEmail = personRepository.findPersonsByEmail(email);
        if (personByEmail.isEmpty()) {
            throw new IllegalStateException("Email ".concat(email).concat(" is not present"));
        } else {
            LOG.debug("Person with email {} found", email);
            return personMapper.personToPersonDto(personByEmail.get());
        }
    }

    @Override
    public PersonDto findPersonByPhone(String phone) {
        Optional<Person> personByPhone = personRepository.findPersonsByPhone(phone);
        if (personByPhone.isEmpty()) {
            throw new IllegalStateException("Phone ".concat(phone).concat(" is not present"));
        } else {
            LOG.debug("Person with phone {} found", phone);
            return personMapper.personToPersonDto(personByPhone.get());
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
            throw new IllegalStateException("Person with ID " + personId + " not found");
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
            LOG.debug("Person identifier updated to {}", identifier);
        }

        final String fullName = personsDto.getFullName();
        if (!Objects.equals(existPerson.getFullName(), fullName) && !isFullnameExist(fullName)) {
            existPerson.setFullName(fullName);
            LOG.debug("Person fullNmae updated to {}", fullName);
        }

        final String address = personsDto.getAddress();
        if (isNotEmpty(address) && !Objects.equals(existPerson.getAddress(), address)) {
            LOG.debug("Person address updated to {}", address);
            existPerson.setAddress(address);
        }

        final String email = personsDto.getEmail();
        if (!Objects.equals(existPerson.getEmail(), email) && !isEmailExist(email)) {
            LOG.debug("Person email updated to {}", email);
            existPerson.setEmail(email);
        }

        final String phone = personsDto.getPhone();
        if (isNotEmpty(phone) && !Objects.equals(existPerson.getPhone(), phone)) {
            existPerson.setPhone(phone);
            LOG.debug("Person phone updated to {}", phone);
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

    private boolean isPhoneBumberExist(String phone) {
        return personRepository.findPersonsByPhone(phone).isPresent();
    }
}
