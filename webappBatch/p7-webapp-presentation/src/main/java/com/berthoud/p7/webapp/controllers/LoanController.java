package com.berthoud.p7.webapp.controllers;

import com.berthoud.p7.webapp.WebApp;
import com.berthoud.p7.webapp.business.managers.LoanManager;
import com.berthoud.p7.webapp.business.managers.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import p7.webapp.model.beans.Customer;

@SessionAttributes(value = "user")
@Controller
public class LoanController {

    @Autowired
    LoanManager loanManager;

    @Autowired
    LoginManager loginManager;


    /**
     * This controller is called when a user try to extend one of its borrowed book.
     *
     * @param model  ---
     * @param loanId the id of the loan to be extended
     * @param user   the registered user
     * @return the member area page is reloaded and a message inform about the success or failure of the loan extension.
     */
    @RequestMapping(value = "/extendLoan", method = RequestMethod.GET)
    public String extendLoan(ModelMap model,
                             @RequestParam int loanId,
                             @SessionAttribute(value = "user") Customer user) {
        WebApp.logger.trace("entering 'extendLoan()");

        int resultExtension = loanManager.extendLoan(loanId);
        String message = new String();

        switch (resultExtension) {
            case 1:
                message = "Le prêt a été prolongé";
                WebApp.logger.info("loan extension successfull");

                break;
            case 0:
                message = "Prolongation impossible, veuillez renouveler votre carte de membre.";
                WebApp.logger.info("failure loan extension / cause: membership expired");

                break;
            case -1:
                message = "Vous avez atteint le nombre max. de prolongations autorisées. ";
                WebApp.logger.info("failure loan extension / cause: max amount of extensions reached");

                break;
        }
        user = loginManager.refreshCustomer(user.getEmail());

        model.addAttribute("user", user);
        model.addAttribute("message", message);

        return "memberArea";
    }

}
