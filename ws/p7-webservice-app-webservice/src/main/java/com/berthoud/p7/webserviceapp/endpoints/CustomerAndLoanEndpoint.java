package com.berthoud.p7.webserviceapp.endpoints;

import com.berthoud.p7.webserviceapp.WebserviceApp;
import com.berthoud.p7.webserviceapp.business.CustomerManager;
import com.berthoud.p7.webserviceapp.business.LoanManager;
import com.berthoud.p7.webserviceapp.business.ReservationManager;
import com.berthoud.p7.webserviceapp.business.exceptions.ServiceFaultException;
import com.berthoud.p7.webserviceapp.business.exceptions.ServiceStatus;
import com.berthoud.p7.webserviceapp.model.entities.*;
import com.berthoud.p7.webserviceapp.ws.customers.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;

import static com.berthoud.p7.webserviceapp.business.Utils.convertLocalDateForXml;
import static com.berthoud.p7.webserviceapp.business.Utils.convertLocalDateTimeForXml;


@Endpoint
@Transactional
public class CustomerAndLoanEndpoint {
    public static final String NAMESPACE_URI = "http://com.berthoud.p7";

    @Autowired
    LoanManager loanManager;

    @Autowired
    CustomerManager customerManager;

    @Autowired
    ReservationManager reservationManager;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginCustomerRequest")
    @ResponsePayload
    public LoginCustomerResponse loginCustomer(@RequestPayload LoginCustomerRequest request) throws ServiceFaultException, DatatypeConfigurationException {

        WebserviceApp.logger.trace("SOAP call loginCustomerRequest");
        LoginCustomerResponse response = new LoginCustomerResponse();

        Customer customer = customerManager.login(request.getEmail(), request.getPassword());


        CustomerWs customerWs = customerMapping(customer);
        response.setCustomer(customerWs);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "refreshCustomerRequest")
    @ResponsePayload
    public RefreshCustomerResponse refreshCustomer(@RequestPayload RefreshCustomerRequest request) throws Exception {

        WebserviceApp.logger.trace("SOAP call refreshCustomerRequest");


        RefreshCustomerResponse response = new RefreshCustomerResponse();

        Customer customer = customerManager.refresh(request.getEmail());
        if (customer == null) {
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setCode("1");
            serviceStatus.setDescription("email wrong");

            throw new ServiceFaultException("refresh denied", serviceStatus);
        }

        CustomerWs customerWs = customerMapping(customer);
        response.setCustomer(customerWs);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "makeReservationRequest")
    @ResponsePayload
    public MakeReservationResponse makeReservation(@RequestPayload MakeReservationRequest request) throws Exception {

        WebserviceApp.logger.trace("SOAP call makeReservationRequest");

        MakeReservationResponse response = new MakeReservationResponse();
        int resultCode = reservationManager.makeReservation(request.getBookReferenceId(), request.getLibrairyId(), request.getCustomerId());

        response.setResultCode(resultCode);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteReservationRequest")
    @ResponsePayload
    public DeleteReservationResponse deleteReservation(@RequestPayload DeleteReservationRequest request) throws Exception {

        WebserviceApp.logger.trace("SOAP call reservationDeleteRequest");

        DeleteReservationResponse response = new DeleteReservationResponse();
        int resultCode = reservationManager.deleteReservation(request.getReservationId());
        response.setResultCode(resultCode);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "extendLoanRequest")
    @ResponsePayload
    public ExtendLoanResponse extendLoan(@RequestPayload ExtendLoanRequest request) {
        WebserviceApp.logger.trace("SOAP call extendLoanRequest");

        ExtendLoanResponse response = new ExtendLoanResponse();
        int resultCode = loanManager.extendLoan(request.getLoanId());
        response.setResultCode(resultCode);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerLoanRequest")
    @ResponsePayload
    public RegisterLoanResponse registerLoan(@RequestPayload RegisterLoanRequest request) {
        WebserviceApp.logger.trace("SOAP call registerLoanRequest");

        RegisterLoanResponse response = new RegisterLoanResponse();
        int resultCode = loanManager.registerNewLoan(request.getCustomerId(), request.getBookId());
        response.setResultCode(resultCode);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerBookReturnRequest")
    @ResponsePayload
    public RegisterBookReturnResponse registerBookBack(@RequestPayload RegisterBookReturnRequest request) throws MessagingException {
        WebserviceApp.logger.trace("SOAP call registerBookReturnRequest");

        RegisterBookReturnResponse response = new RegisterBookReturnResponse();
        int resultCode = loanManager.bookBack(request.getBookId());
        response.setResultCode(resultCode);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllOpenLoansRequest")
    @ResponsePayload
    public GetAllOpenLoansResponse getAllOpenLoans(@RequestPayload GetAllOpenLoansRequest request) throws DatatypeConfigurationException {
        WebserviceApp.logger.trace("SOAP call getAllOpenLoansRequest");

        GetAllOpenLoansResponse response = new GetAllOpenLoansResponse();
        List<Loan> loanList = loanManager.getAllOpenLoans();

        response.getLoans().addAll(loanMapping(loanList));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOpenLoansInTimeRequest")
    @ResponsePayload
    public GetOpenLoansInTimeResponse getOpenLoansInTime(@RequestPayload GetOpenLoansInTimeRequest request) throws DatatypeConfigurationException {
        WebserviceApp.logger.trace("SOAP call getOpenLoansInTimeRequest");

        GetOpenLoansInTimeResponse response = new GetOpenLoansInTimeResponse();
        List<Loan> loanList = loanManager.getOpenLoansInTime();

        response.getLoans().addAll(loanMapping(loanList));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOpenLoansLateRequest")
    @ResponsePayload
    public GetOpenLoansLateResponse getOpenLoansLate(@RequestPayload GetOpenLoansLateRequest request) throws DatatypeConfigurationException {
        WebserviceApp.logger.trace("SOAP call getOpenLoansLateRequest");

        GetOpenLoansLateResponse response = new GetOpenLoansLateResponse();
        List<Loan> loanList = loanManager.getOpenLoansLate();

        response.getLoans().addAll(loanMapping(loanList));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOpenLoansExtendedRequest")
    @ResponsePayload
    public GetOpenLoansExtendedResponse getOpenLoansExtented(@RequestPayload GetOpenLoansExtendedRequest request) throws DatatypeConfigurationException {
        WebserviceApp.logger.trace("SOAP call getOpenLoansExtendedRequest");

        GetOpenLoansExtendedResponse response = new GetOpenLoansExtendedResponse();
        List<Loan> loanList = loanManager.getOpenLoansExtended();

        response.getLoans().addAll(loanMapping(loanList));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationListRequest")
    @ResponsePayload
    public GetReservationListResponse getReservationList (@RequestPayload GetReservationListRequest request) throws DatatypeConfigurationException {
        WebserviceApp.logger.trace("SOAP call getReservationListRequest");

        GetReservationListResponse response = new GetReservationListResponse();
        List<Reservation> reservationList = reservationManager.getAllReservations(request.getBookReferenceId(), request.getLibrairyId());

        response.getReservations().addAll(reservationMapping(reservationList));

        return response;
    }

    /**
     * This method is used to map a list of Loan object into a list of LoanWs object, LoanWs being the web-service-class
     * generated automatically by maven based on the xsd file "customersAndLoans.xsd"
     *
     * @param loanList the list to be converted
     * @return a list of LoanWs object
     * @throws DatatypeConfigurationException
     */
    private List<LoanWs> loanMapping(List<Loan> loanList) throws DatatypeConfigurationException {
        WebserviceApp.logger.trace("entering method loanMapping()");
        List<LoanWs> loanWsList = new ArrayList<>();

        for (Loan l : loanList) {
            LoanWs loanWs = new LoanWs();

            BeanUtils.copyProperties(l, loanWs);
            loanWs.setDateBegin(convertLocalDateForXml(l.getDateBegin()));
            loanWs.setDateEnd(convertLocalDateForXml(l.getDateEnd()));
            loanWs.setDateBack(convertLocalDateForXml(l.getDateBack()));

            Book book = l.getBook();
            BookWs bookWs = new BookWs();
            BeanUtils.copyProperties(book, bookWs);

            BookReferenceWs bookReferenceWs = new BookReferenceWs();
            BeanUtils.copyProperties(book.getBookReference(), bookReferenceWs);
            bookWs.setBookReference(bookReferenceWs);

            bookWs.setDatePurchase(convertLocalDateForXml(book.getDatePurchase()));

            LibrairyWs librairyWs = new LibrairyWs();
            BeanUtils.copyProperties(book.getLibrairy(), librairyWs);
            bookWs.setLibrairy(librairyWs);

            Book.Status status = book.getStatus();
            switch (status) {
                case AVAILABLE:
                    bookWs.setStatus(StatusWs.AVAILABLE);
                    break;
                case BOOKED:
                    bookWs.setStatus(StatusWs.BOOKED);
                    break;
                case BORROWED:
                    bookWs.setStatus(StatusWs.BORROWED);
                    break;
            }

            loanWs.setBook(bookWs);

            CustomerWs customerWs = new CustomerWs();
            BeanUtils.copyProperties(l.getCustomer(), customerWs);
            customerWs.setDateExpirationMembership(convertLocalDateForXml(l.getCustomer().getDateExpirationMembership()));

            loanWs.setCustomerWs(customerWs);
            loanWsList.add(loanWs);
        }
        return loanWsList;
    }


    /**
     * This method is used to map a Reservation object into a ReservationWs Object.
     *
     * @param reservationList the list to be converted
     * @return a list of ReservationWs objects
     * @throws DatatypeConfigurationException
     */
    private List<ReservationWs> reservationMapping(List<Reservation> reservationList) throws DatatypeConfigurationException {
        WebserviceApp.logger.trace("entering method reervationMapping()");

        List<ReservationWs> reservationWsList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            ReservationWs reservationWs = new ReservationWs();

            // 1: copy reservation (primitive type attributes)
            BeanUtils.copyProperties(reservation, reservationWs);

            // copy reservation (LocalDate)
            reservationWs.setDateReservation(convertLocalDateTimeForXml(reservation.getDateReservation()));
            if (reservation.getDateBookAvailableNotification() != null ) {
                reservationWs.setDateBookAvailableNotification(convertLocalDateTimeForXml(reservation.getDateBookAvailableNotification()));
            }

            LibrairyWs librairyWs = new LibrairyWs();
            BeanUtils.copyProperties(reservation.getLibrairy(), librairyWs);
            reservationWs.setLibrairy(librairyWs);

            // copy reservation (nested BookReference)

            // 2 : copy bookReference ( primitive type attributes)
            BookReference bookReference = reservation.getBookReference();
            BookReferenceWs bookReferenceWs = new BookReferenceWs();
            BeanUtils.copyProperties(bookReference, bookReferenceWs);

            //  3: copy nested list of book in BookReference ( primitive type attributes)

            List<Book> bookList = new ArrayList<>(bookReference.getBooks());
            List<BookWs> bookWsList = new ArrayList<>();

            for (Book book : bookList) {
                BookWs bookWs = new BookWs();
                BeanUtils.copyProperties(book, bookWs);

                Book.Status status = book.getStatus();
                switch (status) {
                    case AVAILABLE:
                        bookWs.setStatus(StatusWs.AVAILABLE);
                        break;
                    case BOOKED:
                        bookWs.setStatus(StatusWs.BOOKED);
                        break;
                    case BORROWED:
                        bookWs.setStatus(StatusWs.BORROWED);
                        break;
                }

                LibrairyWs librairyWsOfBookWs = new LibrairyWs();
                BeanUtils.copyProperties(book.getLibrairy(), librairyWsOfBookWs);
                bookWs.setLibrairy(librairyWsOfBookWs);

                List<Loan> loanList = new ArrayList<>(book.getLoans());
                List<LoanWs> loanWsList = new ArrayList<>();

                for (Loan loan : loanList) {
                    LoanWs loanWs = new LoanWs();
                    BeanUtils.copyProperties(loan, loanWs);
                    loanWs.setDateEnd(convertLocalDateForXml(loan.getDateEnd()));
                    loanWsList.add(loanWs);
                }

                bookWs.getLoans().addAll(loanWsList);
                bookReferenceWs.getBooks().add(bookWs);
            }

            reservationWs.setBookReference(bookReferenceWs);
            reservationWsList.add(reservationWs);
        }

        return reservationWsList;
    }

    /**
     * This method is used to map a Customer object into a CustomerWs Object.
     *
     * @param customer the customer to be converted
     * @return CustomerWs
     * @throws DatatypeConfigurationException
     */
    private CustomerWs customerMapping(Customer customer) throws DatatypeConfigurationException {
        WebserviceApp.logger.trace("entering method customerMapping()");

        CustomerWs customerWs = new CustomerWs();

        BeanUtils.copyProperties(customer, customerWs);
        customerWs.setDateExpirationMembership(convertLocalDateForXml(customer.getDateExpirationMembership()));

        List<Loan> loanList = new ArrayList<>(customer.getLoans());
        customerWs.getLoans().addAll(loanMapping(loanList));

        List<Reservation> reservationList = new ArrayList<>(customer.getReservations());
        customerWs.getReservations().addAll(reservationMapping(reservationList));

        return customerWs;
    }
}






