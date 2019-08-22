package com.berthoud.p7.webapp.controllers;

import com.berthoud.p7.webapp.WebApp;
import com.berthoud.p7.webapp.business.managers.LoginManager;
import com.berthoud.p7.webapp.business.managers.ReservationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import p7.webapp.model.beans.BookReference;
import p7.webapp.model.beans.Customer;
import p7.webapp.model.beans.Librairy;

import javax.servlet.http.HttpSession;

@SessionAttributes(value = "user")
@Controller
public class ReservationController {

    @Autowired
    ReservationManager reservationManager;

    @Autowired
    LoginManager loginManager;


    @RequestMapping(value = "/deleteReservation", method = RequestMethod.GET)
    public String deleteReservation(ModelMap model,
                                    @RequestParam int reservationId,
                                    @SessionAttribute(value = "user") Customer user) {


        WebApp.logger.trace("entering 'deleteReservation()");

        int resultDeleteReservation = reservationManager.deleteReservation(reservationId);
        String messageDeleteReservation = new String();

        switch (resultDeleteReservation) {
            case 1:
                messageDeleteReservation = "La réservation a été supprimée";
                WebApp.logger.info("delete reservation was successfull");
                break;

            case -1:
                messageDeleteReservation = "Oups, la réservation n'a pas pus être supprimée. Essayez plus tard.  ";
                WebApp.logger.info("failure delete reservation");
                break;
        }

        user = loginManager.refreshCustomer(user.getEmail());

        model.addAttribute("user", user);
        model.addAttribute("messageDeleteReservation", messageDeleteReservation);

        return "memberArea";

    }

    /**
     * With this method a {@link Customer} can make a reservation for a specific {@link BookReference} ind a specific {@link Librairy},
     * @param model
     * @param librairyId
     * @param bookReferenceId
     * @param afterLogin
     * @param session
     * @return
     * 1    = success (reservation is possible and registered),
     * -1   = failure (customer Id not correct)
     * -2   = failure (Librairy Id not correct)
     * -3   = failure (BookReference Id not correct)
     * -4   = failure: a book with the same BookReference is already currently borrowed by the customer
     * -5   = failure: reservation list is full
     */
    @RequestMapping(value = "/makeReservation", method = RequestMethod.GET)
    public String makeReservation(ModelMap model,
                                  @RequestParam int librairyId,
                                  @RequestParam int bookReferenceId,
                                  @ModelAttribute String afterLogin,
                                  @SessionAttribute(value = "selectedBookReference") BookReference selectedBookReference,

                                  HttpSession session) {


        if (session.getAttribute("user") == null) {
            model.addAttribute("afterLogin", "redirect:makeReservation?librairyId=" + librairyId + "&bookReferenceId=" + bookReferenceId);
            model.addAttribute("toBeDisplayed", "loginForm");
            model.addAttribute("alert", "reservation");
            return "home";

        } else {

            Customer user = (Customer) session.getAttribute("user");

            int resultReservation = reservationManager.makeReservation(user.getId(), bookReferenceId, librairyId);

            model.addAttribute("resultReservation", resultReservation);
            model.addAttribute("selectedBookReference", selectedBookReference);
            return "researchResultDetails";
        }

    }

}

