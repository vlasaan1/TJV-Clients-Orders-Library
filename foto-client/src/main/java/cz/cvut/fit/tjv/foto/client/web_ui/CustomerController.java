package cz.cvut.fit.tjv.foto.client.web_ui;


import cz.cvut.fit.tjv.foto.client.model.CustomerDto;
import cz.cvut.fit.tjv.foto.client.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public String list(Model model){
        var all = customerService.readAll();
        model.addAttribute("allCustomers", all);
        return "customers";
    }

    @GetMapping("/edit")
    public String showForm(@RequestParam Long id, Model model){
        customerService.setCurrentCustomer(id);
        var customer = customerService.readOne().get();
        model.addAttribute("customer", customer);
        return "editCustomer";
    }

    @PostMapping("/edit")
    public String editSubmit(Model model, @ModelAttribute CustomerDto formData) {
        customerService.setCurrentCustomer(formData.getId());
        if(formData.getPhoneNumber().matches(".*[^0-9].*")){
            model.addAttribute("customer", formData);
            model.addAttribute("error", true);
            return "editCustomer";
        }
        try {
            customerService.update(formData);
        } catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", true);
        }
            return "redirect:/customers";
    }


    @GetMapping("/registration")
    public String register(Model model) {
        CustomerDto customer = new CustomerDto(1L, "","");
        model.addAttribute("customerDto", customer);
        return "registration";
    }

    @PostMapping("/registration")
    public  String postSubmit(Model model, @ModelAttribute CustomerDto formData) {

        if(formData.getPhoneNumber().matches(".*[^0-9].*")){
            model.addAttribute("customer", formData);
            model.addAttribute("error", true);
            return "registration";
        }

        customerService.create(formData);
        return  "redirect:/customers";
    }

    @GetMapping("/delete")
    public String deleteCustomer(Model model, @RequestParam Long id){
        customerService.setCurrentCustomer(id);
        customerService.delete();
        return "redirect:/customers";
    }
}
