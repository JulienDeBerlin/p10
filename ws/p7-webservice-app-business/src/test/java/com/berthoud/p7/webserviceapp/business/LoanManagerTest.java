package com.berthoud.p7.webserviceapp.business;

import com.berthoud.p7.webserviceapp.business.batch.reservation.ProcessReservationListTask;
import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.consumer.contract.LoanDAO;
import com.berthoud.p7.webserviceapp.model.entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;


import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanManagerTest {

    @Mock
    private LoanDAO loanDaoMock;

    @InjectMocks
    private LoanManager loanManager;

    static private int maxExtension;
    static private int loanLengthInDays;
    static private int extensionLengthInDays;

    @Before
    public void injectValues() {
        loanManager.setExtensionLengthInDays("28");
        loanManager.setLoanLengthInDays("28");
        loanManager.setMaxExtensions("1");
        maxExtension = parseInt(loanManager.getMaxExtensions());
        loanLengthInDays = parseInt(loanManager.getLoanLengthInDays());
        extensionLengthInDays = parseInt(loanManager.getExtensionLengthInDays());
    }

    @Test
    public void extendLoanTest() {

        Customer customerMembershipValid = new Customer();
        customerMembershipValid.setDateExpirationMembership(LocalDate.now().plusDays(10));

        Customer customerMembershipExpired = new Customer();
        customerMembershipExpired.setDateExpirationMembership(LocalDate.now().minusDays(10));

        // case 1 = open loan, number of extension limit not reached, membership not expired
        Loan loan = new Loan();
        loan.setId(1);
        loan.setDateBack(LocalDate.of(1900, 01, 01));
        LocalDate dateEndBeforeExtension1 = LocalDate.of(2021, 03, 01);
        loan.setDateEnd(dateEndBeforeExtension1);
        loan.setNumberExtensions(maxExtension - 1);
        loan.setCustomer(customerMembershipValid);

        Book book1 = new Book();
        book1.setStatus(Book.Status.BORROWED);
        loan.setBook(book1);

        Optional<Loan> opt = Optional.of(loan);

        when(loanDaoMock.findById(1)).thenReturn(opt);
        assertEquals(loanManager.extendLoan(1), 1);
        assertEquals(loan.getDateEnd(), dateEndBeforeExtension1.plusDays(loanLengthInDays));


        // case 2 : membership expired
        loan.setNumberExtensions(maxExtension - 1);
        loan.setCustomer(customerMembershipExpired);
        Optional<Loan> opt2 = Optional.of(loan);
        when(loanDaoMock.findById(1)).thenReturn(opt2);
        assertEquals(loanManager.extendLoan(1), 0);


        // case 3 :  number of extension limit  reached
        loan.setNumberExtensions(maxExtension);
        loan.setCustomer(customerMembershipValid);
        Optional<Loan> opt3 = Optional.of(loan);
        when(loanDaoMock.findById(1)).thenReturn(opt3);
        assertEquals(loanManager.extendLoan(1), -1);

        // case 4 = non existant loan id
        when(loanDaoMock.findById(99)).thenReturn((Optional.empty()));
        assertEquals(loanManager.extendLoan(99), -2);

        // case 5 = loan is already overdue
        loan.setDateEnd(LocalDate.now().minusDays(3));
        loan.setNumberExtensions(maxExtension - 1);
        Optional<Loan> opt4 = Optional.of(loan);
        when(loanDaoMock.findById(1)).thenReturn(opt4);
        assertEquals(loanManager.extendLoan(1), -3);
    }

}
