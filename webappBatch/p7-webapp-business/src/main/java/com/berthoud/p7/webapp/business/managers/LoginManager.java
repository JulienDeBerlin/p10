package com.berthoud.p7.webapp.business.managers;

import com.berthoud.p7.webapp.business.utils.Utils;
import com.berthoud.p7.webapp.consumer.contracts.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p7.webapp.model.beans.Customer;
import p7.webapp.model.beans.Loan;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@Service
public class LoginManager {

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    ReservationManager reservationManager;

    @Autowired
    LoanManager loanManager;

    /**
     * This method is used for the login of a {@link Customer}.
     *
     * @param email    the email of the {@link Customer}
     * @param password the password of the {@link Customer}
     * @return If the password matches with the email, the corresponding {@link Customer} object is returned.
     */
    public Customer loginCustomer(String email, String password) {
        Utils.loggerWebappBusiness.trace("entering method loginCustomer with param email = " + email);

        Customer customer = customerDAO.getCustomer(email, password);
        loanManager.updateLoans(customer);
        reservationManager.updateReservationList(customer);

        return customer;
    }

    /**
     * This method is used to reload a {@link Customer}, after a change has been made (for instance: extension of a loan)
     *
     * @param email the email of the {@link Customer}
     * @return the updated {@link Customer} object
     */
    public Customer refreshCustomer(String email) {
        Utils.loggerWebappBusiness.trace("entering method refreshCustomer with param email = " + email);

        Customer customer = customerDAO.refreshCustomer(email);
        loanManager.updateLoans(customer);
        reservationManager.updateReservationList(customer);

        return customer;
    }





}
