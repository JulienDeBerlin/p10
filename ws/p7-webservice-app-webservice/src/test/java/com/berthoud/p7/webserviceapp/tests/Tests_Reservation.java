package com.berthoud.p7.webserviceapp.tests;

import com.berthoud.p7.webserviceapp.WebserviceApp;
import com.berthoud.p7.webserviceapp.business.LoanManager;
import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.consumer.contract.ReservationDAO;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebserviceApp.class)

@Transactional

public class Tests_Reservation {

    @Autowired
    ReservationManager reservationManager;

    @Autowired
    LoanManager loanManager;

    @Autowired
    ReservationDAO reservationDAO;


    @Test
    public void getAllReservation() {
        assertEquals(reservationManager.getAllReservations().size(), 1);
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
        assertEquals(reservationManager.getAllReservations(4, 3).size(), 1);
    }


    @Test
    public void getAllReservationByBookReferenceAndLibrairyAndCustomer() {
        assertEquals(reservationManager.getAllReservations(4, 3, 34).size(), 1);
    }


    @Test
    public void reservationListSize() {
        assertFalse(reservationManager.isReservationListFull(4, 3));

        assertEquals(reservationManager.makeReservation(4, 3, 23), 1);

        assertTrue(reservationManager.isReservationListFull(4, 3));
    }


    @Test
    public void isBookWithThisBookReferenceAlreadyBorrowedByCustomer() {
        assertTrue(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(4, 85));
        assertFalse(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(2, 85));
        assertFalse(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(4, 34));
    }


    /**
     * 1    = success (reservation is possible and registered),
     * -1   = failure (customer Id not correct)
     * -2   = failure (Librairy Id not correct)
     * -3   = failure (BookReference Id not correct)
     * -4   = failure: a book with the same BookReference is already currently borrowed by the customer
     * -5   = failure: reservation list is full
     * -6   = failure: the selected BookReference is not available in the selected Librairy
     * -7   = failure: a book with the same BookReference is already currently booked by the customer
     */
    @Test
    public void makeAndDeleteReservation() {

        //Book with BookReference = 3 already borrowed by customer in LibrairyId =2
        assertEquals(reservationManager.makeReservation(3, 2, 34), -4);

        // Attempt to make a reservation for a book not available in the librairy
        assertEquals(reservationManager.makeReservation(4, 1, 34), -6);

        // Attempt to make a reservation for a bookReference already booked by the Customer
        assertEquals(reservationManager.makeReservation(4, 3, 34), -7);

        // Retrieve the current reservation (from step before)
        List<Reservation> reservations = reservationManager.getAllReservations(4, 3, 34);
        int reservationId = reservations.get(0).getId();

        // delete reservation
        reservationManager.deleteReservation(reservationId);

        // New Attempt to make the same reservation as before
        assertEquals(reservationManager.makeReservation(4, 3, 34), 1);

        // 2nd reservation
        assertEquals(reservationManager.makeReservation(4, 3, 86), 1);

        // attempt 3nd reservation : reservation list full
        assertEquals(reservationManager.makeReservation(4, 3, 23), -5);


    }


    @Test
    public void sendNotification0() throws MessagingException, InterruptedException {

        // A book matching with the reservation list if being returned
        assertEquals(loanManager.bookBack(148), 1);
    }



    @Test
    public void sendNotification1() throws MessagingException, InterruptedException {

        // Add a 2nd reservation to the list
        assertEquals(reservationManager.makeReservation(4, 3, 23), 1);

        // A book matching with the reservation list if being returned
        assertEquals(loanManager.bookBack(148), 1);

        //Neither 1st nor 2nd person on the reservation list borrow the returned book
    }


    @Test
    public void sendNotification2() throws MessagingException, InterruptedException {

        // Add a 2nd reservation to the list
        assertEquals(reservationManager.makeReservation(4, 3, 23), 1);

        assertFalse(reservationManager.bookReservedForCustomer(34, 148));

        assertEquals(reservationManager.getAllReservations().size(), 2);

        // A book matching with the reservation list is being returned
        //TODO problème: le thread est mis en pause, et ensuite la réservation est supprimée
        assertEquals(loanManager.bookBack(148), 1);


//
//        assertTrue(reservationManager.bookReservedForCustomer(34, 148));

        //First person on the reservation list borrows the returned book
        assertEquals(loanManager.registerNewLoan(34, 148), 1);
    }


    @Test
    public void sendNotification3() throws MessagingException, InterruptedException {

        // Add a 2nd reservation to the list
        assertEquals(reservationManager.makeReservation(4, 3, 23), 1);

        // A book matching with the reservation list if being returned
        assertEquals(loanManager.bookBack(148), 1);


        //TODO problème: j'aimerais tester les 2 cas ci-desous PENDANT que le Thread est mis en pause

        //The second person on the list tries to borrow the return book after having been notified
        assertEquals(loanManager.registerNewLoan(23, 148), -3);

        //A person with no reservation tries to borrow the return book
        assertEquals(loanManager.registerNewLoan(34, 148), -3);


    }


}
