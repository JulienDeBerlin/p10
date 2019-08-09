package com.berthoud.p7.webserviceapp.tests;

import com.berthoud.p7.webserviceapp.business.CustomerManager;
import com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA.CustomerRepository;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Loan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests_CustomerManagement {

    @Autowired
    CustomerManager customerManager;

    @Autowired
    CustomerRepository customerRepo;


    @Test
    @Transactional
    public void testLogin() {
        try {
            Customer customer1 = customerManager.login("malika@yahoo.fr", "soleil");
            assertNotNull(customer1);


//        Customer customer1 = customerRepo.findById(30).get();

            System.out.println(customer1.getFirstName() + " " + customer1.getSurname() + "  Id=" + customer1.getId());
            System.out.println(customer1.getAddress().getCity());

            Set<Loan> loans = customer1.getLoans();
            loans.forEach(loan -> System.out.println("Titre= " + loan.getBook().getBookReference().getTitle() + "Debut=" + loan.getDateBegin() + "Fin:" + loan.getDateEnd()));
        }catch (Exception e){
            System.out.println(e);
        }

    }


    @Test
    @Transactional
    public void testCustomerRefresh(){
        Customer customer1 = customerManager.refresh("malika@yahoo.fr");
        System.out.println(customer1.getFirstName() + " " + customer1.getSurname() + "  Id=" + customer1.getId());
        System.out.println(customer1.getAddress().getCity());

        Set<Loan> loans = customer1.getLoans();
        loans.forEach(loan -> System.out.println("Titre= " + loan.getBook().getBookReference().getTitle() + "Debut=" + loan.getDateBegin() + "Fin:" + loan.getDateEnd()));



    }


}
