package com.berthoud.p7.webserviceapp.tests;

import com.berthoud.p7.webserviceapp.WebserviceApp;
import com.berthoud.p7.webserviceapp.business.LoanManager;
import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.business.batch.reservation.ScheduledTasks;
import com.berthoud.p7.webserviceapp.consumer.contract.ReservationDAO;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebserviceApp.class)

@Transactional

public class Tests_Reservation {

    @InjectMocks
    @Autowired
    ReservationManager reservationManager;

    @Autowired
    LoanManager loanManager;

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    ScheduledTasks scheduledTasks;

    @Mock
    private Clock clock;

    private final static LocalDate LOCAL_DATE = LocalDate.of(2020, 01, 01);


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        //tell your tests to return the specified LOCAL_DATE when calling LocalDateTime.now(clock)
        Clock fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }


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

        Reservation reservation1NotYetNotified = reservationDAO.findReservationsByBookReferenceLibrairyAndCustomer(4, 3, 34).get(0);

        assertEquals(reservationManager.getAllExpiredReservation().size(), 0);
        assertNull(reservation1NotYetNotified.getDateEndReservation());

        // A book matching with the reservation list if being returned
        assertEquals(loanManager.bookBack(148), 1);

        //reservation1 is notified and a dateEndreservation is set:
        assertNotNull(reservation1NotYetNotified.getDateEndReservation());

        System.out.println(" notification résa1= " + reservation1NotYetNotified.getDateBookAvailableNotification());
        System.out.println("fin de résa pour résa1= " + reservation1NotYetNotified.getDateEndReservation());

        assertEquals(reservationManager.getAllExpiredReservation().size(), 0);

        System.out.println("now1 is = " + LocalDateTime.now());

        Thread.sleep(4000);

        System.out.println("now2 is = " + LocalDateTime.now());

        assertEquals(reservationManager.getAllExpiredReservation().size(), 1);

        // Add a 2nd reservation to the list
        assertEquals(reservationManager.makeReservation(4, 3, 23), 1);


        //With this command...
        scheduledTasks.updateReservationsTask();

        //reservation1 (expired) should be delete and a notification should be sent for reservation2
        assertEquals(reservationManager.getAllExpiredReservation().size(), 0);



    }


    @Test
    public void sendNotification1() throws MessagingException, InterruptedException {

        // Add a 2nd reservation to the list
        assertEquals(reservationManager.makeReservation(4, 3, 23), 1);

        // A book matching with the reservation list if being returned
        assertEquals(loanManager.bookBack(148), 1);



        //1st reservation should have expired, email should be sent to person 2

        scheduledTasks.updateReservationsTask();
    }


    @Test
    public void sendNotification2() throws MessagingException, InterruptedException {

        // Add a 2nd reservation to the list
        assertEquals(reservationManager.makeReservation(4, 3, 23), 1);

        assertFalse(reservationManager.bookReservedForCustomer(34, 148));

        assertEquals(reservationManager.getAllReservations().size(), 2);

        // A book matching with the reservation list is being returned
        assertEquals(loanManager.bookBack(148), 1);

        assertTrue(reservationManager.bookReservedForCustomer(34, 148));

        //First person on the reservation list borrows the returned book
        assertEquals(loanManager.registerNewLoan(34, 148), 1);
    }




}
