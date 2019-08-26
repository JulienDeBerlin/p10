package com.berthoud.p7.webserviceapp.business.batch.reservation;

import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProcessReservationListJob {

    @Autowired
    ReservationManager reservationManager;

    @Autowired
    BookDAO bookDAO;


    public void processReservationList(int bookId) {

        Book returnedBook = bookDAO.findById(bookId).get();
        List<Reservation> reservationList = reservationManager.getAllReservationsByBookId(bookId);


        do {

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

            sendNotificationEmail(customerToBeNotified);
            reservationToBeManaged.setDateBookAvailableNotification(LocalDate.now());

            try {
                TimeUnit.HOURS.sleep(48);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }

            reservationManager.deleteReservation(reservationToBeManaged.getId());

            //refresh returnBook in order to check updated status
            returnedBook = bookDAO.findById(bookId).get();

            //refresh reservationList
            reservationList = reservationManager.getAllReservationsByBookId(bookId);

        } while (!reservationList.isEmpty() && returnedBook.getStatus() == Book.Status.BOOKED);


    }


    private void sendNotificationEmail(Customer customer) {

    }


}
