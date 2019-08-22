package com.berthoud.p7.webapp.controllers;


import com.berthoud.p7.webapp.WebApp;
import com.berthoud.p7.webapp.business.managers.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
     * Displays the member area
     *
     * @param model -
     * @param session -
     * @return the member area page
     */
    @RequestMapping(value = "/displayMemberArea")
    public String displayMemberArea(ModelMap model, HttpSession session) {
        WebApp.logger.trace("entering 'checkLogin()");

        if (session.getAttribute("user") == null) {
            model.addAttribute("toBeDisplayed", "loginForm");
            model.addAttribute("afterLogin", "redirect:displayMemberArea");
            model.addAttribute("alert", "memberArea");
            return "home";
        } else {
            return "memberArea";
        }
    }

    /**
     * This controller is used to perform the login.
     *
     * @param model    ---
     * @param email    the email input
     * @param password the password input
     * @return the memberArea page.
     */
    @RequestMapping(value = "/perfomLogin")
    public String performLogin(ModelMap model,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String afterLogin,
                               HttpSession session) {

        System.out.println("afterlogin = " + afterLogin);

        WebApp.logger.trace("entering 'performLogin()");

        WebApp.logger.info("Email entered by user =" + email);

        Customer user = new Customer();
        try {
            user = loginManager.loginCustomer(email, password);
            session.setAttribute("user", user);
            WebApp.logger.info("login successfull");

        } catch (Exception e) {
            String alert = new String();
            if (e.getMessage().contains("no user registered")) {
                alert = "wrong email";
                WebApp.logger.info("login failure: wrong email");

            }
            if (e.getMessage().contains("login denied")) {
                alert = "wrong password";
                WebApp.logger.info("login failure: wrong password");

            }
            model.addAttribute("alert", alert);
            model.addAttribute("toBeDisplayed", "loginForm");
            return "home";
        }
        return afterLogin;
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
