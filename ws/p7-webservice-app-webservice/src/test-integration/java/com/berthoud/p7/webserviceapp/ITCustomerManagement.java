package com.berthoud.p7.webserviceapp;

import com.berthoud.p7.webserviceapp.business.CustomerManager;
import com.berthoud.p7.webserviceapp.business.exceptions.ServiceFaultException;
import com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA.CustomerRepository;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ITCustomerManagement {

    @Autowired
    CustomerManager customerManager;

    @Autowired
    CustomerRepository customerRepo;

    @Test(expected = RuntimeException.class)
    public void testLogin_wrong_password() throws ServiceFaultException {
        Customer customer1 = customerManager.login("malika@yahoopop.fr", "soleil");
    }

    @Test
    public void testLogin_correct_password() throws ServiceFaultException {
        Customer customer1 = customerManager.login("aicha@yahoo.fr", "soleil");
        assertEquals(customer1.getSurname(), "Djarir");
    }


}







