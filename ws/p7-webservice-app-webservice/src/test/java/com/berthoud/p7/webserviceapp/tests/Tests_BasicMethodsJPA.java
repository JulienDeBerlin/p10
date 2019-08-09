package com.berthoud.p7.webserviceapp.tests;

import com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA.CustomerRepository;
import com.berthoud.p7.webserviceapp.model.entities.Address;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests_BasicMethodsJPA {

    @Autowired
    CustomerRepository customerRepo;

    @Test
    public void testRead() {
        if (customerRepo.findById(4).isPresent()) {
            Customer customer = customerRepo.findById(4).get();
            System.out.println(customer.getSurname());
        }

    }


    @Test
    // no @Transactional required here
    public void testUpdate() {
        Optional<Customer> customer = customerRepo.findById(23);
        if (customer.isPresent()) {
            Customer myCustomer = customer.get();
            myCustomer.setSurname("Dubuquette");
            customerRepo.save(myCustomer);
        }
    }

    @Test
    public void testDelete() {
        if (customerRepo.existsById(3)) {
            customerRepo.deleteById(3);
        }

    }

    @Test
    public void testCount() {
        System.out.println("Total records: =============" + customerRepo.count());

    }

    @Test
    public void testFindBy() {
        List<Customer> listCustomer = customerRepo.findBySurnameIgnoreCase("PierRE");
        for (Customer c : listCustomer) {
            System.out.println(">>>>>>>>>>>>id = " + c.getId());
        }
    }


    @Test
    public void testFindAllJPQL() {
        customerRepo.findAllJPQL().forEach(customerEntity -> System.out.println(customerEntity.getFirstName()));
    }

    @Test
    public void testFindAllPartialData() {
        customerRepo.findAllPartialData().forEach(objects -> System.out.println(objects[0] + "/" + objects[1] + "/" + objects[2]));

    }

}
