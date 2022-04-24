package com.lv.adv.cass.regstr.service;

import com.lv.adv.cass.regstr.model.Persons;
import com.lv.adv.cass.regstr.repository.PersonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonsServiceImplTest {

    @Mock
    private PersonsRepository personsRepository;

    @InjectMocks
    private PersonsServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PersonsServiceImpl(personsRepository);
    }

    @Test
    void deletePerson() {
        UUID id = UUID.randomUUID();
        Persons person = createPerson(id);

        when(personsRepository.findById(id)).thenReturn(Optional.of(person));

        underTest.deletePerson(person.getId());
        verify(personsRepository).deleteById(person.getId());
    }

    @Test
    void updatePerson() {
        UUID id = UUID.randomUUID();
        Persons person = createPerson(id);

        given(personsRepository.findById(id)).willReturn(Optional.of(person));
        underTest.updatePerson(id, "ind123456", "Evgeny Shulgin", "evgeny@inbox.xx", "371 00022244888");

        Optional<Persons> optPerson = personsRepository.findById(id);
        assertThat(optPerson).isPresent().hasValueSatisfying(p -> {
            assertThat(p.getIdentifier()).isEqualTo("ind123456");
            assertThat(p.getFullName()).isEqualTo("Evgeny Shulgin");
            assertThat(p.getEmail()).isEqualTo("evgeny@inbox.xx");
            assertThat(p.getPhone()).isEqualTo("371 00022244888");
        });

    }

    private Persons createPerson(UUID id) {
        return new Persons(id,
                "Jack Dale",
                "ind1",
                "+371 85469777",
                "email@inbox.ll");
    }
}