package com.berthoud.p7.webapp.controllers;

import com.berthoud.p7.webapp.WebApp;
import com.berthoud.p7.webapp.business.managers.LoginManager;
import com.berthoud.p7.webapp.business.managers.ReservationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import p7.webapp.model.beans.Customer;

@SessionAttributes(value = "user")
@Controller
public class ReservationController {

    @Autowired
    ReservationManager reservationManager;

    @Autowired
    LoginManager loginManager;


    @RequestMapping(value = "/deleteReservation", method = RequestMethod.GET)
    public String deleteReservation (ModelMap model,
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

}
