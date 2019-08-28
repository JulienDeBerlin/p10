package com.berthoud.p7.webserviceapp.business.batch.reservation;

import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@PropertySources({
        @PropertySource(value = "classpath:notificationEmail.properties", encoding = "UTF-8")
})
public class ProcessReservationListTask {

    @Autowired
    ReservationManager reservationManager;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    SendNotificationTask sendNotificationTask;

    @Value("${reservationDelayInSecond}")
    int reservationDelayInSecond;


    public void processReservationList(int bookId) throws MessagingException {

        //Get all required objects
        Book returnedBook = bookDAO.findById(bookId).get();

        List<Reservation> reservationList = reservationManager.getAllReservationsByBookId(bookId);

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

        // Send notification email and change status of returned book
        sendNotificationTask.sendNotification(customerToBeNotified, reservationToBeManaged);
//        reservationToBeManaged.setDateBookAvailableNotification(LocalDate.now());
//        reservationToBeManaged.set
        returnedBook.setStatus(Book.Status.BOOKED);




        try {
            TimeUnit.SECONDS.sleep(reservationDelayInSecond);

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        }

        reservationManager.deleteReservation(reservationToBeManaged.getId());


        //refresh returnBook in order to check updated status
        returnedBook = bookDAO.findById(bookId).get();


        //refresh reservationList
        reservationList = reservationManager.getAllReservationsByBookId(bookId);

    }
//        while(!reservationList.isEmpty()&&returnedBook.getStatus()==Book.Status.BOOKED);
//
//        returnedBook.setStatus(Book.Status.AVAILABLE);
}

