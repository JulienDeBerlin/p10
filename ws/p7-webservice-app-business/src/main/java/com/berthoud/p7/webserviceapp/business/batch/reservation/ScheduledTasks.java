package com.berthoud.p7.webserviceapp.business.batch.reservation;

import com.berthoud.p7.webserviceapp.business.BusinessLogger;
import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class ScheduledTasks {

    @Value("${scheduling.isEnabled}")
    private boolean schedulingEnabled;

    @Autowired
    private ReservationManager reservationManager;

    @Autowired
    private ProcessReservationListTask processReservationListTask;


    @Scheduled(fixedRateString = "${scheduling.updateReservationsTask.fixedRate.ms}")
    public void updateReservationsTask() throws MessagingException {

        if (schedulingEnabled) {

            BusinessLogger.logger.info("start scheduled Task: updateReservationsTask");

            List<Reservation> expiredReservationsList = reservationManager.getAllExpiredReservation();
            BusinessLogger.logger.info("nb de reservations ayant expirées =" + expiredReservationsList.size());

            for (Reservation reservation : expiredReservationsList) {
                reservationManager.deleteReservation(reservation.getId());
                if (reservation.getBook().getStatus()!= Book.Status.BORROWED){
                    processReservationListTask.processReservationList(reservation.getBook().getId());
                }
            }
        }
    }
}
