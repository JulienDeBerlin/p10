package com.berthoud.p7.webapp.consumer.ws.impl;

import com.berthoud.p7.webapp.clients.CustomersAndLoansClientWs;
import com.berthoud.p7.webapp.consumer.contracts.CustomerDAO;
import org.springframework.stereotype.Repository;
import p7.webapp.model.beans.Customer;


/**
 * The class is the implementation of the interface {@link CustomerDAO}.
 * The implementation is made by using the webservice client class {@link CustomersAndLoansClientWs}
 */
@Repository
public class CustomerWsImpl extends AbstractWsImpl implements CustomerDAO {

    @Override
    public Customer getCustomer(String email, String password) {
        return customersAndLoansClientWs.getCustomerMapped(email, password);
    }

    @Override
    public Customer refreshCustomer(String email) {
        return customersAndLoansClientWs.refreshCustomerMapped(email);
    }
}
