package com.berthoud.p7.webserviceapp.tests;

import com.berthoud.p7.webserviceapp.WebserviceApp;
import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.consumer.contract.ReservationDAO;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebserviceApp.class)
//@TestPropertySource

@Transactional

public class Tests_Reservation {

    @Autowired
    ReservationManager reservationManager;

    @Autowired
    ReservationDAO reservationDAO;


    @Test
    public void getAllReservation() {
        assertEquals(reservationManager.getAllReservations().size(), 2);
    }


    @Test
    public void getAllReservationByCustomer() {

        // Customer 34 has one reservation
        assertEquals(reservationManager.getAllReservations(34).size(), 1);

        // Customer 348 doesn't exist
        assertEquals(reservationManager.getAllReservations(348).size(), 0);

    }

    @Test
    public void getAllReservationByBookReferenceAndLibrairy() {
        assertEquals(reservationManager.getAllReservations(4, 3).size(), 2);
    }


    @Test
    public void getBooksByReferenceAndLibrairy() {
        assertEquals(reservationManager.getListOfBooksForReferenceAndLibrairy(3, 2).size(), 3);
        assertEquals(reservationManager.getListOfBooksForReferenceAndLibrairy(4, 3).size(), 1);
    }


    @Test
    public void reservationListSize(){
        assertTrue(reservationManager.isReservationListFull(4,3));
    }


    @Test
    public void isBookWithThisBookReferenceAlreadyBorrowedByCustomer(){
        assertTrue(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(4, 85));
        assertFalse(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(2, 85));
        assertFalse(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(4, 34));

    }


    @Test
    public void makeAndDeleteReservation(){

        //Book with BookReference = 3 already borrowed by customer in LibrairyId =2
        assertEquals(reservationManager.makeReservation(3, 2, 34), -4);

        // Attempt to make a reservation for a book not available in the librairy
        assertEquals(reservationManager.makeReservation(4, 1, 34), -6);

        // Reservation ok
        assertEquals(reservationManager.makeReservation(4, 3, 34), 1);

        // Retrieve the reservation that has been persisted in the step before
        List<Reservation> reservations = reservationManager.getAllReservations(4, 3, 34);
        int reservationId = reservations.get(0).getId();

        // reservation list full
        assertEquals(reservationManager.makeReservation(4, 3, 23), -5);

        // delete reservation
        reservationManager.deleteReservation(reservationId);

        // reservation list shouldn't be full anymore
        assertEquals(reservationManager.makeReservation(4, 3, 23), 1);
    }





}
