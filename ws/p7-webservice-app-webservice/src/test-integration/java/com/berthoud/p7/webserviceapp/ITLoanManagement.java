package com.berthoud.p7.webserviceapp;

import com.berthoud.p7.webserviceapp.business.LoanManager;
import com.berthoud.p7.webserviceapp.consumer.contract.LoanDAO;
import com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA.BookRepository;
import com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA.CustomerRepository;
import com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA.LoanRepository;
import com.berthoud.p7.webserviceapp.model.entities.Loan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ITLoanManagement {

    @Autowired
    LoanManager loanManager;

    @Autowired
    LoanRepository loanRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    BookRepository bookRepo;

    @Autowired
    LoanDAO loanDAO;


    @Test
    public void extendLoan() {

        // loan id not correct (no active loan)
        int testValue = loanManager.extendLoan(36);
        assertEquals(testValue, -2);

        // max amount extension reached
        testValue = loanManager.extendLoan(106);
        assertEquals(testValue, -1);

        // loan id not correct
        testValue = loanManager.extendLoan(1);
        assertEquals(testValue, -2);

        // loan is already overdue - no extension possible
        testValue = loanManager.extendLoan(169);
        assertEquals(testValue, -3);

        // membership expired
        testValue = loanManager.extendLoan(40);
        assertEquals(testValue, 0);

    }

    @Test
    public void findBy (){
        assertTrue(loanDAO.findById(169).isPresent());

    }


    @Test
    public void registerNewLoan() {

        // membership expired
        int testValue = loanManager.registerNewLoan(23, 7);
        assertEquals(testValue, 0);

        // book borrowed
        testValue = loanManager.registerNewLoan(23, 4);
        assertEquals(testValue, -3);

        // book booked
        testValue = loanManager.registerNewLoan(34, 2);
        assertEquals(testValue, -3);

        //book id wrong
        testValue = loanManager.registerNewLoan(34, 19);
        assertEquals(testValue, -1);

        // ok
        testValue = loanManager.registerNewLoan(85, 7);
        assertEquals(testValue, 1);
    }


    @Test
    public void bookBack() throws MessagingException {

        //book id wrong
        int testValue = loanManager.bookBack(34);
        assertEquals(testValue, -1);

        //no loan is active
        testValue = loanManager.bookBack(2);
        assertEquals(testValue, 0);

        //return ok
        testValue = loanManager.bookBack(3);
        assertEquals(testValue, 1);
    }


    @Test
    public void testMonitoringLoans() {

        List<Loan> listAllLoans = loanManager.getAllOpenLoans();
        assertEquals(listAllLoans.size(), 9);

        List<Loan> listOpenLoansExtended = loanManager.getOpenLoansExtended();
        assertEquals(listOpenLoansExtended.size(), 4);
    }

}
