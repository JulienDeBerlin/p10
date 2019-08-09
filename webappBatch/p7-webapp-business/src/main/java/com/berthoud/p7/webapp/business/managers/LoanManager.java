package com.berthoud.p7.webapp.business.managers;

import com.berthoud.p7.webapp.business.utils.Utils;
import com.berthoud.p7.webapp.consumer.contracts.LoanDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p7.webapp.model.beans.Customer;
import p7.webapp.model.beans.Loan;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanManager {

    @Autowired
    LoanDAO loanDAO;

    /**
     * The method is used to extend an active {@link Loan}. The extension of a loan is only possible if all following conditions are met:
     * - membership is still valid
     * - max number of extension has not been reached (value to be set in separate settings.properties file)
     * - and of course, if loan id matches with an existing and still open loan.
     * <p>
     * The length of the extension is also to be set in the separate settings.properties file.
     *
     * @param loanId the id of the {@link Loan} to be extended
     * @return :
     * 1 = success (loan has been extended),
     * 0 = failure (membership expired),
     * -1 = failure (max amount of extensions reached),
     * -2 = failure (loanId not correct)
     */
    public int extendLoan(int loanId) {
        Utils.loggerWebappBusiness.trace("entering method extendLoan with loanId = " + loanId);

        return loanDAO.extendLoan(loanId);
    }


    /**
     * This method is used for the loan monitoring.
     *
     * @return a list of {@link Loan} objects for which the return deadline has been reached.
     */
    public List<Loan> getListOpenLoansLate() {
        Utils.loggerWebappBusiness.trace("entering getListOpenLoansLate()");

        return loanDAO.getListOpenLoansLate();
    }


    /**
     * A {@link Loan} object has among its attributes an attribute {@link Customer}, which is the person who borrowed the book.
     * <p>
     * This method takes as input a list of {@link Loan} objects and outputs the matching list of {@link Customer} objects,
     * where each Customer object has as attribute the list of his loans as stated in the input list.
     *
     * @param loanList the reference loanlist that should be used to generate the Customer list
     * @return a list of {@link Customer} objects
     */
    public List<Customer> convertLoanListIntoCustomerList(List<Loan> loanList) {
        Utils.loggerWebappBusiness.trace("entering method convertLoanListIntoCustomerList");


        List<Customer> customers = new ArrayList<>();

        for (Loan l : loanList) {
            int index = customers.indexOf(l.getCustomer());
            if (index != -1) {
                Customer customerToBeUpdated = customers.get(index);
                customerToBeUpdated.getLoans().add(l);
            } else {
                Customer customerToBeAdded = l.getCustomer();
                customerToBeAdded.getLoans().add(l);
                customers.add(customerToBeAdded);
            }
        }

        return customers;
    }

}
