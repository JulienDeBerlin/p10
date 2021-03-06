package com.berthoud.p7.webapp.controllers;

import com.berthoud.p7.webapp.WebApp;
import com.berthoud.p7.webapp.business.managers.BookResearchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import p7.webapp.model.beans.Librairy;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes(value = "librairyList")
public class HomeController {

    @Autowired
    BookResearchManager bookResearchManager;

    /**
     * Displays the home page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        WebApp.logger.trace("entering 'home()");
        return "home";
    }

    /**
     * Displays the book-research-form inside the home page
     */
    @RequestMapping(value = "/researchForm", method = RequestMethod.GET)
    public String displayResearchForm(ModelMap model) {
        WebApp.logger.trace("entering 'displayResearchForm()");
        model.addAttribute("toBeDisplayed", "researchForm");
        List<Librairy> librairyList = bookResearchManager.getAllLibrairies();
        model.addAttribute(librairyList);
        return "home";
    }


}