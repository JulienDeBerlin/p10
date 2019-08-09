package com.berthoud.p7.batch;

import com.berthoud.p7.webapp.business.managers.LoanManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import p7.webapp.model.beans.Customer;

import java.util.List;

import static org.junit.Assert.assertEquals;

@PropertySource("classpath:messages.properties")
@RunWith(SpringRunner.class)
@SpringBootTest
public class P7BatchApplicationTests {

    @Value("${salutations}")
    private String salutations;

    @Value("${level3}")
    private String delayLevel3;


    @Autowired
    LoanManager loanManager;


    @Test
    public void contextLoads() {
    }

    @Test
    public void conversionLoanListIntoCustomerList() {
        List<Customer> customerList = loanManager.convertLoanListIntoCustomerList(loanManager.getListOpenLoansLate());
        assertEquals(customerList.size(), 2);

    }

    @Test
    public void readProperties() {
        String test = salutations;

        System.out.println(test);

//        assertEquals(test,"Cher usager,");

    }

    @Test
    public void readProperties2() {


        System.out.println(delayLevel3);
        int test = Integer.parseInt(delayLevel3);
        assertEquals(test, 35);

    }

}
