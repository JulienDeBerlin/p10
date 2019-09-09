package com.berthoud.p7.webserviceapp.business;

import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.consumer.contract.ReservationDAO;
import com.berthoud.p7.webserviceapp.model.entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static junit.framework.TestCase.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationManagerTest {

    @Mock
    ReservationDAO reservationDAO;

    @Mock
    BookDAO bookDAO;

    @Mock
    BookResearchManager bookResearchManager;

    @Mock
    LoanManager loanManager;

    @InjectMocks
    ReservationManager reservationManager;

    private static int reservationListLengthFactor;


    @Before
    public void setUp() {
        reservationManager.setReservationListLengthFactor("2");
        reservationListLengthFactor = parseInt(reservationManager.getReservationListLengthFactor());
    }


    @Test
    public void TestIsReservationListFull() {

        List<Book> bookList = new ArrayList<>();
        List <Reservation> reservationList = new ArrayList<>();

        //bookList with 3 elements
        bookList.add(new Book());
        bookList.add(new Book());
        bookList.add(new Book());

        //Reservationlist with 5 elements
        reservationList.add(new Reservation());
        reservationList.add(new Reservation());
        reservationList.add(new Reservation());
        reservationList.add(new Reservation());
        reservationList.add(new Reservation());

        when(bookResearchManager.getListOfBooksForReferenceAndLibrairy(anyInt(), anyInt())).thenReturn(bookList);
        when(reservationDAO.findReservationsByBookReferenceAndLibrairy(anyInt(), anyInt())).thenReturn(reservationList);
        assertFalse(reservationManager.isReservationListFull(3, 4));

        //Reservationlist with 6 elements
        reservationList.add(new Reservation());
        assertTrue(reservationManager.isReservationListFull(3, 4));

        reservationManager.setReservationListLengthFactor("3");
        assertFalse(reservationManager.isReservationListFull(3, 4));

        //Reservationlist with 9 elements
        reservationList.add(new Reservation());
        reservationList.add(new Reservation());
        reservationList.add(new Reservation());
        assertTrue(reservationManager.isReservationListFull(3, 4));
    }

    @Test
    public void TestIsBookWithThisBookReferenceAlreadyBorrowedByCustomer(){

        BookReference bookReferenceA = new BookReference();
        bookReferenceA.setId(1);

        BookReference bookReferenceB = new BookReference();
        bookReferenceB.setId(2);

        Book bookA = new Book();
        bookA.setBookReference(bookReferenceA);

        Book bookB = new Book();
        bookB.setBookReference(bookReferenceB);

        Customer customer = new Customer();
        customer.setId(99);

        List<Loan> listOpenLoanMock = new ArrayList<>();

        when(loanManager.getAllOpenLoans()).thenReturn(listOpenLoanMock);

        // case 1 : customer has no open loan
        assertFalse(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(1, 99));

        // case 2 customer has 1 openloan that DO NOT MATCH with the bookReference
        Loan loanB1 = new Loan();
        loanB1.setBook(bookB);
        loanB1.setCustomer(customer);
        listOpenLoanMock.add(loanB1);
        assertFalse(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(1, 99));

        // case 3 customer has 1 openloan that DO MATCH with the bookReference
        Loan loanA1 = new Loan();
        loanA1.setBook(bookA);
        loanA1.setCustomer(customer);
        listOpenLoanMock.add(loanA1);
        assertTrue(reservationManager.isBookWithThisBookReferenceAlreadyBorrowedByCustomer(1, 99));

    }


    @Test
    public void TestNextCustomerToBeNotified(){

        Librairy librairy = new Librairy();
        librairy.setId(1);

        Customer customer1 = new Customer();
        customer1.setId(1);
        Customer customer2 = new Customer();
        customer2.setId(2);
        Customer customer3 = new Customer();
        customer3.setId(3);

        BookReference bookReferenceA = new BookReference();
        bookReferenceA.setId(1);

        Book bookA = new Book();
        bookA.setId(10);
        bookA.setBookReference(bookReferenceA);
        bookA.setLibrairy(librairy);

        when(bookDAO.findById(anyInt())).thenReturn(Optional.of(bookA));

        // case 1 : 3 customers have made the same reservation (same bookreference, same Librairy), no one has been notified yet
        List <Reservation> reservationList = new ArrayList<>();

        Reservation reservation1 = new Reservation();
        reservation1.setCustomer(customer1);
        reservation1.setId(1);
        reservation1.setLibrairy(librairy);
        reservation1.setBookReference(bookReferenceA);
        reservation1.setDateReservation(LocalDateTime.of(2018, 12, 2, 12, 23, 32, 213));
        reservationList.add(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setCustomer(customer2);
        reservation2.setLibrairy(librairy);
        reservation2.setBookReference(bookReferenceA);
        reservation2.setDateReservation(LocalDateTime.of(2018, 12, 2, 12, 25, 32, 213));
        reservationList.add(reservation2);

        Reservation reservation3 = new Reservation();
        reservation3.setId(3);
        reservation3.setCustomer(customer3);
        reservation3.setLibrairy(librairy);
        reservation3.setBookReference(bookReferenceA);
        reservation3.setDateReservation(LocalDateTime.of(2018, 12, 4, 12, 23, 32, 213));
        reservationList.add(reservation3);

        when(reservationDAO.findReservationsByBookReferenceAndLibrairy(anyInt(), anyInt())).thenReturn(reservationList);
        assertEquals(reservationManager.getNextCustomerToBeNotified(1).getId(), 1);

        reservation3.setDateReservation(LocalDateTime.of(2017, 12, 4, 12, 23, 32, 213));
        assertEquals(reservationManager.getNextCustomerToBeNotified(1).getId(), 3);


        // case 2 : same as before, but customer 3 has been notified yet
        reservation3.setDateBookAvailableNotification(LocalDateTime.of(2019, 01, 03, 16, 23));
        assertEquals(reservationManager.getNextCustomerToBeNotified(1).getId(), 1);

        // case 3: all have been notified
        reservation1.setDateBookAvailableNotification(LocalDateTime.of(2019, 01, 04, 16, 23));
        reservation2.setDateBookAvailableNotification(LocalDateTime.of(2019, 01, 04, 16, 23));
        assertEquals(reservationManager.getNextCustomerToBeNotified(1).getId(), 0);
    }


    @Test
    public void TestIsBookReservedForCustomer(){

        Librairy librairy = new Librairy();
        librairy.setId(1);

        Customer customer = new Customer();
        customer.setId(99);

        BookReference bookReferenceA = new BookReference();
        bookReferenceA.setId(1);

        Book bookA = new Book();
        bookA.setId(1);
        bookA.setBookReference(bookReferenceA);
        bookA.setLibrairy(librairy);

        when(bookDAO.findById(anyInt())).thenReturn(Optional.of(bookA));

        List <Reservation> reservationListForLibrairyForReferenceAndForCustomer = new ArrayList<>();
        when(reservationDAO.findReservationsByBookReferenceLibrairyAndCustomer(anyInt(), anyInt(), anyInt())).thenReturn(reservationListForLibrairyForReferenceAndForCustomer);

        // case 1 : Customer has no reservation
        assertFalse(reservationManager.isBookReservedForCustomer(99, 1));

        // case 2 : Customer has reservation BUT NO BOOK has yet been linked with the reservation
        Reservation reservation1 = new Reservation();
        reservation1.setCustomer(customer);
        reservationListForLibrairyForReferenceAndForCustomer.add(reservation1);
        assertFalse(reservationManager.isBookReservedForCustomer(99, 1));

        // case 3 : Customer has reservation and A BOOK has been linked with the reservation
        Reservation reservation2 = new Reservation();
        reservation2.setCustomer(customer);
        reservation2.setBook(bookA);
        reservation2.setDateBookAvailableNotification(LocalDateTime.now());
        reservationListForLibrairyForReferenceAndForCustomer.add(reservation2);
        assertTrue(reservationManager.isBookReservedForCustomer(99, 1));

    }


}
