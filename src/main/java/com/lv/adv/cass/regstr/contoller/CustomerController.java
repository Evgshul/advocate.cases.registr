package com.lv.adv.cass.regstr.contoller;

import com.lv.adv.cass.regstr.dto.CustomerDto;
import com.lv.adv.cass.regstr.model.Customer;
import com.lv.adv.cass.regstr.service.CustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    public final CustomersService customersService;

    @GetMapping
    public String listCustomers(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String personIdentifier,
            @RequestParam(required = false) String representativeIdentifier,
            Model model
    ) {
        List<CustomerDto> customers = customersService.findCustomers(companyName, registrationNumber, phone, email, personIdentifier, representativeIdentifier);

        model.addAttribute("customers", customers);
        model.addAttribute("companyName", companyName);
        model.addAttribute("registrationNumber", registrationNumber);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);
        model.addAttribute("personIdentifier", personIdentifier);
        model.addAttribute("representativeIdentifier", representativeIdentifier);
        return "customers/globalSearch";
    }
}
