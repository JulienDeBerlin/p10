package com.berthoud.p7.webapp.controllers;


import com.berthoud.p7.webapp.business.managers.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import p7.webapp.model.beans.Customer;

@SessionAttributes ( value = "user")
@Controller
public class LoginController {

    @Autowired
    LoginManager loginManager;

    @RequestMapping(value = "/memberArea", method = RequestMethod.POST)
    public String getMemberArea(ModelMap model,
                                @RequestParam String email,
                                @RequestParam String password) {

        Customer user = new Customer();
        try{
             user = loginManager.loginCustomer(email, password);
        }catch (Exception e){

        }

        model.addAttribute("user", user);

        return "memberAreaDemo";

    }

}
