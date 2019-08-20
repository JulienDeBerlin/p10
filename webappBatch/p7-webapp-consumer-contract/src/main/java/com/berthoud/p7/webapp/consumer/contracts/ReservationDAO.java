package com.berthoud.p7.webapp.consumer.contracts;

import p7.webapp.model.beans.Reservation;

import java.util.List;

public interface ReservationDAO {

    List<Reservation> getReservationList (int bookReferenceId, int librairyId);

    int deleteReservation (int reservationId);
}
