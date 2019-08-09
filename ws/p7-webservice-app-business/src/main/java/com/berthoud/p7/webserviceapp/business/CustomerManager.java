package com.berthoud.p7.webserviceapp.business;

import com.berthoud.p7.webserviceapp.business.exceptions.ServiceFaultException;
import com.berthoud.p7.webserviceapp.business.exceptions.ServiceStatus;
import com.berthoud.p7.webserviceapp.consumer.contract.CustomerDAO;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * This class is dedicated to the management of librairy user, called here customers.
 */

@Service
public class CustomerManager {

    private static int workload = 12;


    @Autowired
    CustomerDAO customerDAO;

    /**
     * This method is used for the login of a customer. It checks whether the candidate password matches with the
     * hashed password stored in the db.
     *
     * @param email    the email of the customer
     * @param password the candidate password entered by the customer
     * @return If the password is correct, the Customer object is returned.
     */
    public Customer login(String email, String password) throws ServiceFaultException {
        BusinessLogger.logger.trace("entering method login with param email = " + email);

        Optional<Customer> customerOptional = customerDAO.findByEmail(email);
        if (customerOptional.isPresent()) {
            if (checkPasswordBCrypt(password, customerOptional.get().getPassword())) {
                BusinessLogger.logger.info("login with email = " + email + " was successfull");
                return customerOptional.get();
            } else {
                ServiceStatus serviceStatus = new ServiceStatus();
                serviceStatus.setCode("-1");
                serviceStatus.setDescription("the password is not correct.");
                BusinessLogger.logger.info("failure login with email = " + email + ", cause: wrong password");
                BusinessLogger.logger.error("ServiceFaultException, login denied");
                throw new ServiceFaultException("login denied", serviceStatus);


            }
        } else {
            BusinessLogger.logger.error("RuntimeException, no user registered under this email");
            throw new RuntimeException("no user registered under this email");
        }
    }

    /**
     * This method is used to refresh the active customer, based on its email.
     *
     * @param email the email of the customer
     * @return the Customer object
     */
    public Customer refresh(String email) {
        BusinessLogger.logger.trace("entering method refresh with param email = " + email);

        Optional<Customer> customerOptional = customerDAO.findByEmail(email);
        return customerOptional.get();
    }


    /**
     * This method can be used to generate a string representing an account password
     * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
     * hash string of length=60
     * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
     * A workload of 12 is a very reasonable safe default as of 2013.
     * This automatically handles secure 128-bit salt generation and storage within the hash.
     *
     * @param password_plaintext The account's plaintext password as provided during account creation,
     *                           or when changing an account's password.
     * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
     */
    public static String hashPasswordBCrypt(String password_plaintext) {
        BusinessLogger.logger.trace("entering method hashPasswordBCrypt with param password_plaintext");

        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return (hashed_password);
    }


    /**
     * This method can be used to verify a computed hash from a plaintext (e.g. during a login
     * request) with that of a stored hash from a database. The password hash from the database
     * must be passed as the second variable.
     *
     * @param password_plaintext The account's plaintext password, as provided during a login request
     * @param stored_hash        The account's stored password hash, retrieved from the authorization database
     * @return boolean - true if the password matches the password of the stored hash, false otherwise
     */
    public static boolean checkPasswordBCrypt(String password_plaintext, String stored_hash) {
        BusinessLogger.logger.trace("entering method checkPasswordBCrypt");

        boolean password_verified = false;

        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            BusinessLogger.logger.error("IllegalArgumentException, Invalid hash provided for comparison\"");
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);
        BusinessLogger.logger.info("password ok");

        return (password_verified);
    }

}
