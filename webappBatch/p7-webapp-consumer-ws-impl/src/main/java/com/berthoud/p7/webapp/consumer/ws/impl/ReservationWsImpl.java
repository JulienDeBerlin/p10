package com.berthoud.p7.webapp.consumer.ws.impl;

import com.berthoud.p7.webapp.consumer.contracts.ReservationDAO;
import org.springframework.stereotype.Repository;
import p7.webapp.model.beans.Reservation;

import java.util.List;


@Repository
public class ReservationWsImpl  extends AbstractWsImpl implements ReservationDAO {

    @Override
    public List<Reservation> getReservationList(int bookReferenceId, int librairyId) {
        return customersAndLoansClientWs.getReservationListMapped(bookReferenceId, librairyId);
    }

    @Override
    public int deleteReservation(int reservationId) {
        return customersAndLoansClientWs.deleteReservationMapped(reservationId);
    }

    @Override
    public int makeReservation(int customerId, int bookReferenceId, int librairyId) {
        return customersAndLoansClientWs.makeReservationMapped(customerId, bookReferenceId, librairyId);
    }
}
