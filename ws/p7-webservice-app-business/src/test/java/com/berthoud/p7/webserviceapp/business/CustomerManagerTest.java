package com.berthoud.p7.webserviceapp.business;

import org.junit.Test;

import static org.junit.Assert.*;

import static com.berthoud.p7.webserviceapp.business.CustomerManager.checkPasswordBCrypt;

public class CustomerManagerTest {

    private static CustomerManager customerManager = new CustomerManager();

    @Test
    public void hashAndCheckPassword() {
        String inputHashed = customerManager.hashPasswordBCrypt("soleil");
        assertTrue(checkPasswordBCrypt("soleil", inputHashed));
        assertFalse(checkPasswordBCrypt("lune", inputHashed));
    }


}
