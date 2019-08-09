package com.berthoud.p7.webserviceapp.endpoints;

import com.berthoud.p7.webserviceapp.WebserviceApp;
import com.berthoud.p7.webserviceapp.business.CustomerManager;
import com.berthoud.p7.webserviceapp.business.LoanManager;
import com.berthoud.p7.webserviceapp.business.exceptions.*;
import com.berthoud.p7.webserviceapp.business.exceptions.ServiceStatus;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Loan;
import com.berthoud.p7.webserviceapp.ws.customers.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;

import static com.berthoud.p7.webserviceapp.utils.Utils.convertLocalDateForXml;


@Endpoint
@Transactional
public class CustomerAndLoanEndpoint {
    public static final String NAMESPACE_URI = "http://com.berthoud.p7";


    @Autowired
    LoanManager loanManager;

    @Autowired
    CustomerManager customerManager;


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
        if (customer == null){
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setCode("1");
            serviceStatus.setDescription("email wrong");

            throw new ServiceFaultException("refresh denied", serviceStatus);
        }

        CustomerWs customerWs = customerMapping(customer);
        response.setCustomer(customerWs);
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
    public RegisterBookReturnResponse registerBookBack(@RequestPayload RegisterBookReturnRequest request) {
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

        return customerWs;
    }
}






