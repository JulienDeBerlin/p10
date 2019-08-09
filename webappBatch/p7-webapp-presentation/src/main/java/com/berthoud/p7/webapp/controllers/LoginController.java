package com.berthoud.p7.webapp.controllers;


import com.berthoud.p7.webapp.WebApp;
import com.berthoud.p7.webapp.business.managers.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import p7.webapp.model.beans.Customer;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    LoginManager loginManager;

    /**
     * This controller is used to perform the login.
     *
     * @param model    ---
     * @param email    the email input
     * @param password the password input
     * @return the memberArea page.
     */
    @RequestMapping(value = "/memberArea", method = RequestMethod.POST)
    public String getMemberArea(ModelMap model,
                                @RequestParam String email,
                                @RequestParam String password,
                                HttpSession session) {

        WebApp.logger.trace("entering 'getMemberArea()");

        WebApp.logger.info("Email entered by user =" + email);


        Customer user = new Customer();
        try {
            user = loginManager.loginCustomer(email, password);
            session.setAttribute("user", user);
            WebApp.logger.info("login successfull");


        } catch (Exception e) {
            String alert = new String();
            if ( e.getMessage().contains("no user registered")){
                alert = "wrong email";
                WebApp.logger.info("login failure: wrong email");

            }
            if ( e.getMessage().contains("login denied")){
                alert = "wrong password";
                WebApp.logger.info("login failure: wrong password");

            }
            model.addAttribute("alert", alert);
            model.addAttribute("toBeDisplayed", "loginForm");
            return "home";
        }

        return "memberArea";

    }

    /**
     * Remove user session attribute
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        WebApp.logger.trace("entering 'logout()");
        session.removeAttribute("user");

        WebApp.logger.trace("log out completed");
        return "home";
    }


}
