package com.berthoud.p7.webapp.consumer.contracts;

import p7.webapp.model.beans.Customer;

public interface CustomerDAO {

    Customer getCustomer(String email, String password);
    Customer refreshCustomer(String email);

}
