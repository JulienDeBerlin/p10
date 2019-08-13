package com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA;

import com.berthoud.p7.webserviceapp.consumer.contract.ReservationDAO;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends CrudRepository <Reservation, Integer>, ReservationDAO {


    @Query ("select r FROM Reservation r JOIN r.customer c where c.id =:customerId")
    List<Reservation> findReservationsByCustomer(@Param(value = "customerId") int customerId);

    @Query ("select r FROM Reservation r JOIN r.bookReference b JOIN r.librairy l where b.id =:bookReferenceId and l.id =:librairyId")
    List<Reservation> findReservationsByBookReferenceAndLibrairy(@Param(value = "bookReferenceId")int bookReferenceId,
                                                                 @Param(value = "librairyId") int librairyId);

    @Query ("select r FROM Reservation r JOIN r.bookReference b JOIN r.librairy l " +
            "JOIN r.customer c where b.id =:bookReferenceId and l.id =:librairyId and c.id =:customerId")
    List<Reservation> findReservationsByBookReferenceLibrairyAndCustomer(@Param(value = "bookReferenceId")int bookReferenceId,
                                                                         @Param(value = "librairyId") int librairyId,
                                                                         @Param(value = "customerId")int customerId);


}
