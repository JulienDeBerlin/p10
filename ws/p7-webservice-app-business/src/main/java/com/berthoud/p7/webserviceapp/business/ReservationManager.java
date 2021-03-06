package com.berthoud.p7.webserviceapp.business;


import com.berthoud.p7.webserviceapp.consumer.contract.*;
import com.berthoud.p7.webserviceapp.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;


/**
 * This class is dedicated to the management of reservations.
 */


@Service
@PropertySource("classpath:application.properties")

public class ReservationManager {


    @Autowired
    LoanManager loanManager;

    @Autowired
    BookResearchManager bookResearchManager;

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    BookReferenceDAO bookReferenceDAO;

    @Autowired
    LibrairyDAO librairyDAO;

    @Autowired
    Clock clock;

    /**
     * For a specific BookReference in a specific Librairy, only a limited amount of reservations can be performed
     * equals to amount of books for this reference and librairy x reservationListLengthFactor
     */
    @Value("${reservationListLengthFactor}")
    private String reservationListLengthFactor;

    public String getReservationListLengthFactor() {
        return reservationListLengthFactor;
    }

    public void setReservationListLengthFactor(String reservationListLengthFactor) {
        this.reservationListLengthFactor = reservationListLengthFactor;
    }

    /**
     * Retrieves all actual reservations
     *
     * @return a list of all {@link Reservation} objects
     */
    public List<Reservation> getAllReservations() {
        return reservationDAO.findAll();
    }

    /**
     * Retrieves all actual reservations for a specific {@link com.berthoud.p7.webserviceapp.model.entities.Customer}
     *
     * @param customerId -
     * @return a list of all {@link Reservation} objects
     */
    public List<Reservation> getAllReservations(int customerId) {
        return reservationDAO.findReservationsByCustomer(customerId);

    }

    /**
     * This method retrieves a reservationList for a given bookId
     *
     * @param bookId -
     * @return
     */
    public List<Reservation> getAllReservationsByBookId(int bookId) {

        List<Reservation> reservationList = new ArrayList<>();

        Optional<Book> b = bookDAO.findById(bookId);
        if (b.isPresent()) {
            Book book = b.get();
            reservationList = getAllReservations(book.getBookReference().getId(), book.getLibrairy().getId());
        }
        return reservationList;
    }

    /**
     * Retrieves all actual reservations for a specific {@link com.berthoud.p7.webserviceapp.model.entities.Librairy}
     * and a specific {@link com.berthoud.p7.webserviceapp.model.entities.BookReference}
     *
     * @param bookReferenceId -
     * @param librairyId      -
     * @return a list of all {@link Reservation} objects
     */
    public List<Reservation> getAllReservations(int bookReferenceId, int librairyId) {
        return reservationDAO.findReservationsByBookReferenceAndLibrairy(bookReferenceId, librairyId);
    }

    /**
     * Retrieves all actual reservations for a specific {@link com.berthoud.p7.webserviceapp.model.entities.Librairy}
     * a specific {@link com.berthoud.p7.webserviceapp.model.entities.BookReference} and a specific
     * {@link com.berthoud.p7.webserviceapp.model.entities.Customer}
     *
     * @param bookReferenceId -
     * @param librairyId      -
     * @param customerId      -
     * @return a list of all {@link Reservation} objects
     */
    public List<Reservation> getAllReservations(int bookReferenceId, int librairyId, int customerId) {
        return reservationDAO.findReservationsByBookReferenceLibrairyAndCustomer(bookReferenceId, librairyId, customerId);
    }

    /**
     * Retrieves all reservation which have expired
     *
     * @return
     */
    public List<Reservation> getAllExpiredReservation() {
        LocalDateTime now = LocalDateTime.now();
        return reservationDAO.findBydateEndReservationLessThan(now);
    }


    /**
     * This method is used to register a new reservation
     *
     * @param bookReferenceId -
     * @param librairyId      -
     * @param customerId      -
     * @return 1    = success (reservation is possible and registered),
     * -1   = failure (customer Id not correct)
     * -2   = failure (Librairy Id not correct)
     * -3   = failure (BookReference Id not correct)
     * -4   = failure: a book with the same BookReference is already currently borrowed by the customer
     * -5   = failure: reservation list is full
     * -6   = failure: the selected BookReference is not available in the selected Librairy
     * -7   = failure: this BookReference is already currently reserved by the customer
     */
    public int makeReservation(int bookReferenceId, int librairyId, int customerId) {
        BusinessLogger.logger.trace("entering method makeReservation with param bookReferenceId =" + bookReferenceId +
                "librairyId =" + librairyId + "and customerId =" + customerId);

        Optional<Customer> customer = customerDAO.findById(customerId);
        if (!customer.isPresent()) {
            BusinessLogger.logger.info("reservation impossible, cause: customer id " + customerId + "not correct ");
            return -1;
        }

        Optional<Librairy> librairy = librairyDAO.findById(librairyId);
        if (!librairy.isPresent()) {
            BusinessLogger.logger.info("reservation impossible, cause: librairyId " + librairyId + "not correct ");
            return -2;
        }

        Optional<BookReference> bookReference = bookReferenceDAO.findById(bookReferenceId);
        if (!bookReference.isPresent()) {
            BusinessLogger.logger.info("reservation impossible, cause: bookReferenceId " + bookReferenceId + "not correct ");
            return -3;
        }

        if (bookResearchManager.getListOfBooksForReferenceAndLibrairy(bookReferenceId, librairyId).size() == 0) {
            BusinessLogger.logger.info("reservation impossible, cause: book with bookReferenceId " + bookReferenceId +
                    "is not available in librairy with" + librairyId);
            return -6;
        }


        if (isBookWithThisBookReferenceAlreadyBorrowedByCustomer(bookReferenceId, customerId)) {
            BusinessLogger.logger.info(" reservation impossible, cause: book with this reference already currently" +
                    " borrowed by customer.");
            return -4;
        }

        if (isReservationListFull(bookReferenceId, librairyId)) {
            BusinessLogger.logger.info(" reservation impossible, cause: reservation list already full!");
            return -5;
        }

        List<Reservation> reservationListCustomer = reservationDAO.findReservationsByCustomer(customerId);
        for (Reservation reservation : reservationListCustomer) {
            if (reservation.getBookReference().getId() == bookReferenceId) {
                BusinessLogger.logger.info(" reservation impossible, cause: the user has already a similar reservation open");
                return -7;
            }
        }

        // create new reservation
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer.get());
        reservation.setBookReference(bookReference.get());
        reservation.setDateReservation(LocalDateTime.now());
        reservation.setLibrairy(librairy.get());

        reservationDAO.save(reservation);
        return 1;

    }

    /**
     * This method is used to deleted an existing reservation
     *
     * @param reservationId
     * @return 1    = success (reservation deleted ),
     * -1   = failure (reservation id not correct)
     */
    public int deleteReservation(int reservationId) {

        Optional<Reservation> reservation = reservationDAO.findById(reservationId);
        if (!reservation.isPresent()) {
            BusinessLogger.logger.info(" reservation delete impossible, cause: reservationId " + reservationId + " is not valid ");
            return -1;
        } else {
            reservationDAO.deleteById(reservationId);
            return 1;
        }

    }

    /**
     * This method checks if the reservation list for a specific {@link com.berthoud.p7.webserviceapp.model.entities.BookReference}
     * in a specific {@link com.berthoud.p7.webserviceapp.model.entities.Librairy} is full.
     *
     * @param bookReferenceId -
     * @param librairyId      -
     * @return true if the reservation list is full and no new reservations can be registered anymore.
     */
    public boolean isReservationListFull(int bookReferenceId, int librairyId) {
        int amountBooksForThisReferenceInThisLibrairy = bookResearchManager.getListOfBooksForReferenceAndLibrairy(bookReferenceId, librairyId).size();
        int amountReservationsForThisReferenceInThisLibrairy = getAllReservations(bookReferenceId, librairyId).size();
        return amountReservationsForThisReferenceInThisLibrairy == Integer.parseInt(reservationListLengthFactor) * amountBooksForThisReferenceInThisLibrairy;
    }


    /**
     * A user is not allowed to make a reservation for a BookReference if he currently has an open loan for this bookReference.
     *
     * @param bookReferenceId -
     * @param customerId      -
     * @return true if the specified customer has an active loan matching with the specified bookReference
     */
    public boolean isBookWithThisBookReferenceAlreadyBorrowedByCustomer(int bookReferenceId, int customerId) {
        boolean bookCurrentlyBorrowed = false;
        List<Loan> loanList = loanManager.getAllOpenLoans();
        for (Loan l : loanList) {
            if (l.getCustomer().getId() == customerId && l.getBook().getBookReference().getId() == bookReferenceId) {
                bookCurrentlyBorrowed = true;
                break;
            }
        }
        return bookCurrentlyBorrowed;
    }

    /**
     * This method checks whether a given Book is currently reserved for a given Customer
     *
     * @param customerId -
     * @param bookId     -
     * @return
     */
    public boolean isBookReservedForCustomer(int customerId, int bookId) {

        Optional<Book> bookOptional = bookDAO.findById(bookId);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            List<Reservation> reservationList = this.getAllReservations(book.getBookReference().getId(), book.getLibrairy().getId(), customerId);

            if (!reservationList.isEmpty()){
                for (int i = 0; i < reservationList.size(); i++) {
                    if (reservationList.get(i).getDateBookAvailableNotification() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method retrieves the next Customer on a reservation list to be notified for a given bookId
     *
     * @param bookId -
     * @return A customer. If reservation list is empty, an empty customer object is retrieved.
     */
    public Customer getNextCustomerToBeNotified(int bookId) {

        List<Reservation> reservationList = getAllReservationsByBookId(bookId);
        reservationList.sort(Comparator.comparing(Reservation::getDateReservation));

        Customer customer = new Customer();
        for (int i = 0; i < reservationList.size(); i++) {
            if (reservationList.get(i).getDateBookAvailableNotification() == null) {
                customer = reservationList.get(i).getCustomer();
                break;
            }
        }
        return customer;
    }


}
