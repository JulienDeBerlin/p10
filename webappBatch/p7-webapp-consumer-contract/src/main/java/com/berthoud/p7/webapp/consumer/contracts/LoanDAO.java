package com.berthoud.p7.webapp.consumer.contracts;

import p7.webapp.model.beans.Loan;

import java.util.List;


public interface LoanDAO {

    int extendLoan(int loanId);

    List<Loan> getListOpenLoansLate();

}
