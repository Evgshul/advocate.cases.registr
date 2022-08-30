package com.lv.adv.cass.regstr.contoller;

import com.lv.adv.cass.regstr.dto.PersonsDto;
import com.lv.adv.cass.regstr.model.Persons;
import com.lv.adv.cass.regstr.repository.PersonsRepository;
import com.lv.adv.cass.regstr.service.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Controller
public class PersonsController {

    @Autowired
    private PersonsService personsService;

    private PersonsRepository personsRepository;

    @GetMapping("/")
    public String personsList(Model model) {
        model.addAttribute("listPersons", personsService.getPersons());
        return "person/personsList";
    }

    @GetMapping("/personsForm")
    public String addPerson(Model model) {
        Persons persons = new Persons();
        model.addAttribute("person", persons);
        return "person/addPerson";
    }

    @PostMapping("/savePerson")
    public String savePerson(@ModelAttribute("person") @Valid Persons person, BindingResult result) {
        if (result.hasErrors()) {
            return "person/addPerson";
        } else {
            personsService.addPerson(person);
            return "redirect:/";
        }
    }

    @GetMapping("/person/edit/{id}")
    public String editPerson(@PathVariable("id") UUID id, Model model) {
        Persons existedPerson = personsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid " +
                        "user ID: " + id));
        model.addAttribute("person", existedPerson);
        return "person/update_person";
    }

    @PostMapping("/person/update/{id}")
    public String updatePerson(@PathVariable("id") UUID id, final PersonsDto personsNewValue) {

        personsService.updatePerson(id,
                personsNewValue.getIdentifier(),
                personsNewValue.getFullName(),
                personsNewValue.getAddress(),
                personsNewValue.getEmail(),
                personsNewValue.getPhone());

        return EMPTY;
    }
}
