package com.berthoud.p7.webapp.business.managers;

import com.berthoud.p7.webapp.consumer.contracts.ReservationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p7.webapp.model.beans.*;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class ReservationManager {

    @Autowired
    ReservationDAO reservationDAO;

    /**
     * This methods is used to update all the reservations made by a customer with the following informations : the next planned return date and the position in the reservation list.
     *
     * @param customer the customer object for which the reservation list should be updated
     * @return
     */
    public Customer updateReservationList(Customer customer) {

        List<Reservation> reservationList = customer.getReservations();
        Iterator<Reservation> iterator = reservationList.iterator();

        while (iterator.hasNext()) {
            Reservation r = iterator.next();
            r.setPlannedNextreturn(calculateNextReturnDate(r));
            r.setPositionInreservationList(calculatePositionInReservationList(r));
        }
        return customer;
    }

    /**
     * This method takes a reservation object as parameter and retrieve the closest date, when a book with the same
     * BookReference as the reservation is planned to be returned.
     *
     * @param reservation -
     * @return
     */
    private LocalDate calculateNextReturnDate(Reservation reservation) {

        LocalDate nextDateEnd = LocalDate.now().plusYears(1000);

        Set<Book> bookListMatchingWithReservation = reservation.getBookReference().getBooks();

        for (Book book : bookListMatchingWithReservation) {
            for (Loan loan : book.getLoans()) {
                if (book.getStatus() == Book.Status.BORROWED && loan.getDateEnd().isBefore(nextDateEnd)) {
                    nextDateEnd = loan.getDateEnd();
                }
            }
        }

        return nextDateEnd;
    }


    /**
     * This method takes a list of loans as parameter and retrieve the closest date, when a book of the list
     * is planned to be returned.
     *
     * @param loanList the list of loans
     * @return
     */
    LocalDate calculateNextReturnDate(List<Loan> loanList) {

        LocalDate nextDateEnd = LocalDate.now().plusYears(1000);

        for (Loan loan : loanList) {
            if (loan.getDateEnd().isBefore(nextDateEnd)) {
                nextDateEnd = loan.getDateEnd();
            }
        }
        return nextDateEnd;
    }


    /**
     * A reservation list is hold for each specific combinaison Bookrefence and Libraiy.
     * This method takes a reservation as parameter and calculate its position in the matching reservation list.
     *
     * @param reservation -
     * @return the position in the reservation list.
     */
    private int calculatePositionInReservationList(Reservation reservation) {

        int positionInReservationList = 1;

        for (Reservation r : getReservationList(reservation.getBookReference().getId(), reservation.getLibrairy().getId())) {
            if (r.getDateReservation().isBefore(reservation.getDateReservation())) {
                positionInReservationList++;
            }

            if (r.getDateReservation().isEqual(reservation.getDateReservation()) && r.getId() < reservation.getId()) {
                positionInReservationList++;
            }
        }

        return positionInReservationList;
    }

    /**
     * This method retrieves a list of Reservation objects, based on a Bookreference and a Librairy.
     *
     * @param bookReferenceId -
     * @param librairyId      -
     * @return a list of Reservation objects
     */
    private List<Reservation> getReservationList(int bookReferenceId, int librairyId) {
        return reservationDAO.getReservationList(bookReferenceId, librairyId);
    }


    /**
     * This method deletes a reservation and returns a code as result
     *
     * @param reservationId
     * @return 1 for successful deletion, -1 for failure
     */
    public int deleteReservation(int reservationId) {
        return reservationDAO.deleteReservation(reservationId);

    }


    /**
     * With this method a {@link Customer} can make a reservation for a specific {@link BookReference} ind a specific {@link Librairy},
     *
     * @param customerId      -
     * @param bookReferenceId -
     * @param librairyId      -
     * @return
     * 1    = success (reservation is possible and registered),
     * -1   = failure (customer Id not correct)
     * -2   = failure (Librairy Id not correct)
     * -3   = failure (BookReference Id not correct)
     * -4   = failure: a book with the same BookReference is already currently borrowed by the customer
     * -5   = failure: reservation list is full
     * -6   = failure: the selected BookReference is not available in the selected Librairy
     * -7   = failure: this BookReference is already currently reserved by the customer
     */
    public int makeReservation(int customerId, int bookReferenceId, int librairyId){
        return reservationDAO.makeReservation(customerId, bookReferenceId, librairyId);
    }

}
