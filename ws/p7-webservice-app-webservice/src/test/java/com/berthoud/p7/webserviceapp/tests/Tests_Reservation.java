package com.berthoud.p7.webserviceapp.tests;

import com.berthoud.p7.webserviceapp.WebserviceApp;
import com.berthoud.p7.webserviceapp.business.LoanManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest (classes = WebserviceApp.class)
@Transactional

public class Tests_Reservation {

    @Autowired
    LoanManager loanManager;



    @Test
    public void registerReservation(){


    }


}
