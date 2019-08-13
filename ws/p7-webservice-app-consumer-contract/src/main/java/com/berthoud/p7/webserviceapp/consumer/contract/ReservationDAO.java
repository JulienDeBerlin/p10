package com.berthoud.p7.webserviceapp.consumer.contract;


import com.berthoud.p7.webserviceapp.model.entities.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationDAO {

    List<Reservation> findAll();

    List<Reservation> findReservationsByBookReferenceAndLibrairy(int bookReferenceId, int librairyId);

    List<Reservation> findReservationsByCustomer(int customerId);

    List<Reservation> findReservationsByBookReferenceLibrairyAndCustomer(int bookReferenceId, int librairyId, int customerId);

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(int id);

    void deleteById (int id);

}
