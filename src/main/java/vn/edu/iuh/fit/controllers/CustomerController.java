package vn.edu.iuh.fit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model){
        if(customerService.login(email,password)!=null){
            return "redirect:/product";
        }
        return "redirect:/customer/login";
    }
}
