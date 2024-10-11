package com.lv.adv.cass.regstr.contoller;

import com.lv.adv.cass.regstr.dto.PersonDto;
import com.lv.adv.cass.regstr.model.Person;
import com.lv.adv.cass.regstr.repository.PersonRepository;
import com.lv.adv.cass.regstr.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.UUID;


@Controller
public class PersonsController {

    @Autowired
    private PersonService personsService;

    private PersonRepository personsRepository;

    @GetMapping("/")
    public String personsList(Model model) {
        model.addAttribute("listPersons", personsService.getAllPersons());
        return "person/personsList";
    }

    @GetMapping("/personsForm")
    public String addPerson(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "person/addPerson";
    }

    @PostMapping("/savePerson")
    public String savePerson(@ModelAttribute("person") @Valid PersonDto person, BindingResult result) {
        if (result.hasErrors()) {
            return "person/addPerson";
        } else {
            personsService.addPerson(person);
            return "redirect:/";
        }
    }

    @GetMapping("/person/edit/{id}")
    public String editPerson(@PathVariable("id") UUID id, Model model) {
        Person existedPerson = personsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid " +
                        "user ID: " + id));
        model.addAttribute("person", existedPerson);
        return "person/update_person";
    }

    @PostMapping("/person/update/{id}")
    public Person updatePerson(@PathVariable("id") UUID id, final PersonDto personsNewValue) {

        return personsService.updatePerson(id, personsNewValue);

    }
}
