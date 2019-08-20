package com.berthoud.p7.webapp.clients;


import customersAndLoans.wsdl.*;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import p7.webapp.model.beans.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.berthoud.p7.webapp.utils.Utils.convertXmlDateToLocal;


/**
 * This class consumes the webservices offered by the wsdl books.wsdl
 */
public class CustomersAndLoansClientWs extends WebServiceGatewaySupport {


    /**
     * This method is used to retrieve a customer, using a webservice.
     *
     * @param email    the email of the Customer
     * @param password the password of the Customer
     * @return a webservice {@link LoginCustomerRequest} object
     */
    public LoginCustomerResponse getCustomerWs(String email, String password) {
        LoginCustomerRequest request = new LoginCustomerRequest();
        request.setEmail(email);
        request.setPassword(password);
        LoginCustomerResponse loginCustomerResponse = (LoginCustomerResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return loginCustomerResponse;
    }

    /**
     * This method retrieves a customer, using a webservice. It maps then the result into a {@link Customer} object.
     *
     * @param email    the email of the Customer
     * @param password the password of the Customer
     * @return a {@link Customer} object
     */
    public Customer getCustomerMapped(String email, String password) {

        CustomerWs customerWs = getCustomerWs(email, password).getCustomer();
        return customerMapping(customerWs);
    }

    /**
     * This method is used to refresh a customer, based on its email
     *
     * @param email the email of the customer
     * @return the CustomerWs object
     */
    public RefreshCustomerResponse refreshCustomerWs(String email) {
        RefreshCustomerRequest request = new RefreshCustomerRequest();
        request.setEmail(email);
        return (RefreshCustomerResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    /**
     * This method retrieves a customer, using a webservice. It maps then the result into a {@link Customer} object.
     *
     * @param email the email of the Customer
     * @return a {@link Customer} object
     */
    public Customer refreshCustomerMapped(String email) {
        CustomerWs customerWs = refreshCustomerWs(email).getCustomer();
        return customerMapping(customerWs);
    }


    /**
     * This method is used to extend a loan using a webservice.
     *
     * @param loanId the id of the Loan to be extended
     * @return a webservice {@link ExtendLoanResponse} object
     */
    public ExtendLoanResponse extendLoanWs(int loanId) {
        ExtendLoanRequest request = new ExtendLoanRequest();
        request.setLoanId(loanId);
        return (ExtendLoanResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }


    /**
     * This method is used to extend a loan using a webservice. It then maps the result into a {@link Loan} object.
     *
     * @param loanId the id of the Loan to be extended
     * @return a {@link Loan} object
     */
    public int extendLoanMapped(int loanId) {
        return extendLoanWs(loanId).getResultCode();
    }


    /**
     * This method is used for the loan monitoring, using a webservice.
     *
     * @return a webservice {@link GetOpenLoansLateResponse} object
     */
    public GetOpenLoansLateResponse getOpenLoansLateResponseWs() {
        GetOpenLoansLateRequest request = new GetOpenLoansLateRequest();
        return (GetOpenLoansLateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    /**
     * This method is used for the loan monitoring.
     *
     * @return a list of {@link Loan} objects for which the return deadline has been reached.
     */
    public List<Loan> getOpenLoansLateResponseMapped() {

        List<LoanWs> openLoansLateWs = getOpenLoansLateResponseWs().getLoans();
        List<Loan> openLoansLate = new ArrayList<>();

        for (LoanWs loanWs : openLoansLateWs) {
            Loan loan = new Loan();

            loan = loanMapping(loanWs);

            Customer customer = new Customer();
            BeanUtils.copyProperties(loanWs.getCustomerWs(), customer);
            customer.setDateExpirationMembership(convertXmlDateToLocal(loanWs.getCustomerWs().getDateExpirationMembership()));
            loan.setCustomer(customer);
            openLoansLate.add(loan);
        }

        return openLoansLate;
    }

    /**
     * This method is used for the loan monitoring, using a webservice.
     *
     * @return a webservice {@link GetOpenLoansLateResponse} object, that includes all open loans.
     */
    public GetAllOpenLoansResponse getAllOpenLoans(){
        GetAllOpenLoansRequest request = new GetAllOpenLoansRequest();
        return (GetAllOpenLoansResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    /**
     * This method is used for the loan monitoring, using a webservice.
     *
     * @return a list of all open loans.
     */
    public List<Loan> getAllOpenLoansMapped(){

        List<Loan> openLoans = new ArrayList<>();
        List<LoanWs> openLoansWs = getAllOpenLoans().getLoans();

        for (LoanWs loanWs :openLoansWs) {
            openLoans.add(loanMapping(loanWs));
        }
        return openLoans;
    }




    /**
     * This method returns a list of Reservation based on a BookReference and a Librairy, using a webservice.
     *
     * @param bookReferenceId -
     * @param librairyId      -
     * @return a webservice {@link GetReservationListResponse} object
     */
    public GetReservationListResponse getReservationListWs(int bookReferenceId, int librairyId) {
        GetReservationListRequest request = new GetReservationListRequest();
        request.setBookReferenceId(bookReferenceId);
        request.setLibrairyId(librairyId);
        return (GetReservationListResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }


    /**
     * This method is used to retrieve of list of reservations based on a BookReference and a Librairy, using a webservice.
     * It then maps the result into a list of {@link Reservation} object.
     *
     * @param bookReferenceId -
     * @param librairyId      -
     * @return a list of {@link Reservation} object
     */
    public List<Reservation> getReservationListMapped(int bookReferenceId, int librairyId) {
        List<ReservationWs> reservationWsList = getReservationListWs(bookReferenceId, librairyId).getReservations();
        List<Reservation> reservationList = new ArrayList<>();

        for (ReservationWs reservationWs : reservationWsList) {
            reservationList.add(reservationMapping(reservationWs));
        }
        return reservationList;
    }


    /**
     * This method deletes a reservation using a webservice
     *
     * @param reservationId
     * @return
     */
    public DeleteReservationResponse deleteReservation(int reservationId) {
        DeleteReservationRequest request = new DeleteReservationRequest();
        request.setReservationId(reservationId);
        return (DeleteReservationResponse) getWebServiceTemplate().marshalSendAndReceive(request);

    }

    /**
     * This method deletes a reservation using a webservice and returns a code as result
     *
     * @param reservationId
     * @return 1 for successful deletion, -1 for failure
     */
    public int deleteReservationMapped(int reservationId) {
        return deleteReservation(reservationId).getResultCode();
    }


    /**
     * Mapping of a CustomerWs object into a Customer object.
     *
     * @param customerWs the object to be mapped
     * @return
     */
    private Customer customerMapping(CustomerWs customerWs) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerWs, customer);
        customer.setDateExpirationMembership(convertXmlDateToLocal(customerWs.getDateExpirationMembership()));


        List<Loan> loanList = new ArrayList<>();
        for (LoanWs loanWs : customerWs.getLoans()) {
            loanList.add(loanMapping(loanWs));
        }
        customer.setLoans(loanList);


        List<Reservation> reservationList = new ArrayList<>();
        for (ReservationWs reservationWs : customerWs.getReservations()) {
            reservationList.add(reservationMapping(reservationWs));
        }
        customer.setReservations(reservationList);

        return customer;
    }


    /**
     * Mapping of a LoanWs object into a Loan object.
     *
     * @param loanWs the object to be mapped
     * @return
     */
    private Loan loanMapping(LoanWs loanWs) {
        Loan loan = new Loan();
        BeanUtils.copyProperties(loanWs, loan);
        loan.setDateBegin(convertXmlDateToLocal(loanWs.getDateBegin()));
        loan.setDateEnd(convertXmlDateToLocal(loanWs.getDateEnd()));
        loan.setDateBack(convertXmlDateToLocal(loanWs.getDateBack()));

        Book book = new Book();
        BookWs bookWs = loanWs.getBook();

        BeanUtils.copyProperties(bookWs, book);

        BookReference bookReference = new BookReference();
        BeanUtils.copyProperties(bookWs.getBookReference(), bookReference);
        book.setBookReference(bookReference);

        Librairy librairy = new Librairy();
        LibrairyWs librairyWs = loanWs.getBook().getLibrairy();
        BeanUtils.copyProperties(librairyWs, librairy);
        book.setLibrairy(librairy);

        loan.setBook(book);

        return loan;
    }

    /**
     * Mapping of a ReservationWs object into a Reservation object.
     *
     * @param reservationWs
     * @return
     */
    private Reservation reservationMapping(ReservationWs reservationWs) {
        Reservation reservation = new Reservation();

        BeanUtils.copyProperties(reservationWs, reservation);
        reservation.setDateReservation(convertXmlDateToLocal(reservationWs.getDateReservation()));
        if (reservationWs.getDateBookAvailableNotification() != null) {
            reservation.setDateBookAvailableNotification(convertXmlDateToLocal(reservationWs.getDateBookAvailableNotification()));
        }

        Librairy librairy = new Librairy();
        LibrairyWs librairyWs = reservationWs.getLibrairy();
        BeanUtils.copyProperties(librairyWs, librairy);
        reservation.setLibrairy(librairy);

        BookReference bookReference = new BookReference();
        BookReferenceWs bookReferenceWs = reservationWs.getBookReference();

        BeanUtils.copyProperties(bookReferenceWs, bookReference);

        List<Book> bookList = new ArrayList<>();
        List<BookWs> bookWsList = bookReferenceWs.getBooks();

        for (BookWs bookWs : bookWsList) {
            Book book = new Book();
            BeanUtils.copyProperties(bookWs, book);

            StatusWs status = bookWs.getStatus();
            switch (status) {
                case AVAILABLE:
                    book.setStatus(Book.Status.AVAILABLE);
                    break;
                case BOOKED:
                    book.setStatus(Book.Status.BOOKED);
                    break;
                case BORROWED:
                    book.setStatus(Book.Status.BORROWED);
                    break;
            }


            List<Loan> loanList = new ArrayList<>();
            List<LoanWs> loanWsList = bookWs.getLoans();

            for (LoanWs loanWs : loanWsList) {
                Loan loan = new Loan();
                BeanUtils.copyProperties(loanWs, loan);
                loan.setDateEnd(convertXmlDateToLocal(loanWs.getDateEnd()));
                loanList.add(loan);
            }

            book.setLoans(new HashSet<>(loanList));
            bookList.add(book);
        }

        bookReference.setBooks(new HashSet<>(bookList));
        reservation.setBookReference(bookReference);
        return reservation;
    }
}
