package com.berthoud.p7.webserviceapp.business.batch.reservation;

import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;

@Service
public class ProcessReservationListTask {

    @Autowired
    ReservationManager reservationManager;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    Clock clock;

    @Autowired
    SendNotificationTask sendNotificationTask;

    @Value("${reservationDelayUnit}")
    String reservationDelayUnit;

    @Value("${reservationDelayAmount}")
    int reservationDelayAmount;


    public void processReservationList(int bookId) throws MessagingException {

        // Get required objects
        Book returnedBook = bookDAO.findById(bookId).get();
        List<Reservation> reservationList = reservationManager.getAllReservationsByBookId(bookId);

        if (!reservationList.isEmpty()) {

            Customer customerToBeNotified = reservationManager.getNextCustomerToBeNotified(bookId);

            Reservation reservationToBeManaged = new Reservation();
            Iterator<Reservation> iterator = reservationList.iterator();
            while (iterator.hasNext()) {
                Reservation reservation = iterator.next();
                if (reservation.getCustomer().getId() == customerToBeNotified.getId()) {
                    reservationToBeManaged = reservation;
                    break;
                }
            }

            // Send notification email
            sendNotificationTask.sendNotification(customerToBeNotified, reservationToBeManaged);

            // Update reservationToBeManaged
            reservationToBeManaged.setDateBookAvailableNotification(LocalDateTime.now());
            reservationToBeManaged.setDateEndReservation(reservationToBeManaged.getDateBookAvailableNotification().plus(reservationDelayAmount, ChronoUnit.valueOf(reservationDelayUnit)));
            reservationToBeManaged.setBook(returnedBook);

            // Change status of returned book
            returnedBook.setStatus(Book.Status.BOOKED);

        } else {
            returnedBook.setStatus(Book.Status.AVAILABLE);
        }
    }


}

