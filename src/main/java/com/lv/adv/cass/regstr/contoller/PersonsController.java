package com.lv.adv.cass.regstr.contoller;

import com.lv.adv.cass.regstr.model.Persons;
import com.lv.adv.cass.regstr.service.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonsController {

    @Autowired
    private PersonsService personsService;

    @GetMapping("/")
    public String personsList(Model model) {
        model.addAttribute("listPersons", personsService.getAllPersons());
        return "personsList";
    }

    @GetMapping("/personsForm")
    public String addPerson(Model model) {
        Persons persons = new Persons();
        model.addAttribute("person", persons);
        return "addPerson";
    }

    @PostMapping("/savePerson")
    public String savePerson(@ModelAttribute("person") Persons person) {
        personsService.addPerson(person);
        return "redirect:/";
    }
}
