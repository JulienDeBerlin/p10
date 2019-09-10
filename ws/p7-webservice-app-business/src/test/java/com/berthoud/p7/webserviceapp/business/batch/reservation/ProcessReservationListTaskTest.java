package com.berthoud.p7.webserviceapp.business.batch.reservation;

import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.consumer.contract.ReservationDAO;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ProcessReservationListTaskTest {

    @Mock
    BookDAO bookDAO;

    @Mock
    ReservationManager reservationManager;

    @Mock
    SendNotificationTask sendNotificationTask;

    @InjectMocks
    ProcessReservationListTask processReservationListTask;

    @Before
    public void injectValues(){
       processReservationListTask.setReservationDelayAmount(48);
       processReservationListTask.setReservationDelayUnit("HOURS");
    }

    @Test
    public void testLaunchProcessReservation() throws MessagingException {

        Book book = new Book();
        book.setId(1);

        Customer customer = new Customer();
        customer.setId(10);

        List <Reservation> reservationList = new ArrayList<>();
        when(bookDAO.findById(anyInt())).thenReturn(Optional.of(book));
        when(reservationManager.getAllReservationsByBookId(anyInt())).thenReturn(reservationList);
        when(reservationManager.getNextCustomerToBeNotified(anyInt())).thenReturn(customer);

        // case 1 : reservationlist is empty
        processReservationListTask.processReservationList(1);
        assertEquals(book.getStatus(), Book.Status.AVAILABLE);

        // case 2 : reservationlist is not empty
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setDateBookAvailableNotification(LocalDateTime.of(2019, 12, 02, 23, 01));
        reservationList.add(reservation);
        processReservationListTask.processReservationList(1);
        assertEquals(book.getStatus(), Book.Status.BOOKED);

    }
}
