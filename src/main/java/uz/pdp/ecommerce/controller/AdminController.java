package uz.pdp.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ecommerce/admin")
public class AdminController {
    @GetMapping("/")
    public String getAdmin(){
        return "admin";
    }
}
