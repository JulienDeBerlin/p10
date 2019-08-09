package com.berthoud.p7.batch.lateLoansMonitoring;

import com.berthoud.p7.batch.P7BatchApplication;
import com.berthoud.p7.webapp.business.managers.LoanManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import p7.webapp.model.beans.Customer;
import p7.webapp.model.beans.Loan;

import java.util.List;


/**
 * This class is designed for a task, a task being part of a batch job.
 * The task designed here is about retrieving a list of {@link Customer} objects, who should receive a reminder email.
 */

@Component
public class GetListLateUserTask {

    @Autowired
    LoanManager loanManager;

    /**
     * @return this method retrieves a list of {@link Customer} objects, who should receive a reminder email.
     */
    public List<Customer> getListLateUserTask() {
        P7BatchApplication.logger.trace("Enter getListLateUserTask(), Task 'getListLateUserTask' is starting");

        List<Loan> loanLateList = loanManager.getListOpenLoansLate();
        List<Customer> customerLateList = loanManager.convertLoanListIntoCustomerList(loanLateList);

        return customerLateList;
    }
}
