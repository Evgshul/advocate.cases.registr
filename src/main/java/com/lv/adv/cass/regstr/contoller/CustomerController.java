package com.lv.adv.cass.regstr.contoller;

import com.lv.adv.cass.regstr.dto.CustomerDto;
import com.lv.adv.cass.regstr.repository.CustomersRepository;
import com.lv.adv.cass.regstr.service.CustomersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    public final CustomersService customersService;
    private final CustomersRepository customersRepository;

    @GetMapping
    public String listCustomers(
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        List<CustomerDto> customers = customersService.findCustomers(keyword);
        model.addAttribute("customers", customers);
        model.addAttribute("keyword", keyword);
        return "customers/globalSearch";
    }

    @GetMapping("/create")
    public String createCustomerForm(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "customers/create"; // Points to `templates/customers/create.html`

    }

    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "customers/create";
        }
        customersService.addNewCustomer(customerDto);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Customer %s created successfully!", customerDto.getCompanyName()));
        return "redirect:/customers";
    }

    @GetMapping("/editCustomer/{id}")
    public String updateCustomerForm(@PathVariable("id") UUID id, Model model) {
        CustomerDto customerDto = customersService.findByCustomerId(id);
        model.addAttribute("customerDto", customerDto);
        return "cuxtomer/editCustomer";
    }

    @PostMapping("/editCustomer/{id}")
    public String updateCustomer(
            @PathVariable("id") UUID id,
            @Valid @ModelAttribute("customerDto") CustomerDto customerDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if(bindingResult.hasErrors()) {
            return "customer/editCustomer";
        }

        customersService.updateCustomer(id, customerDto);
        redirectAttributes.addFlashAttribute("successMessage",
                "Customer updated successfully");
        return "redirect:/customers";
    }
}
