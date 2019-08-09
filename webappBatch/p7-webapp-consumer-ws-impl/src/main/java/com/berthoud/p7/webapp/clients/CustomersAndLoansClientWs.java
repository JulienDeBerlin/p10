package com.berthoud.p7.webapp.clients;


import customersAndLoans.wsdl.*;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import p7.webapp.model.beans.Book;
import p7.webapp.model.beans.BookReference;
import p7.webapp.model.beans.Customer;
import p7.webapp.model.beans.Loan;

import java.util.ArrayList;
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
        return (LoginCustomerResponse) getWebServiceTemplate().marshalSendAndReceive(request);
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
        loan.setBook(book);

        return loan;
    }

}
