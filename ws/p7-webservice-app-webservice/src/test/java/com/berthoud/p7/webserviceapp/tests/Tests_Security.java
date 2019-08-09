package com.berthoud.p7.webserviceapp.tests;


import com.berthoud.p7.webserviceapp.business.CustomerManager;
import com.berthoud.p7.webserviceapp.consumer.contract.CustomerDAO;
import com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA.CustomerRepository;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests_Security {


    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    CustomerManager customerManager;


    @Test
    public void updateCustomerWithHashPassword() {

        Optional<Customer> customer = customerRepo.findById(86);
        if (customer.isPresent()) {
            Customer myCustomer = customer.get();
            myCustomer.setPassword(CustomerManager.hashPasswordBCrypt(myCustomer.getPassword()));
            customerRepo.save(myCustomer);
        }

    }


    @Test
    public void testLogin() {

        try {
            customerManager.login("malika@yahoo.fr", "soleil");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
