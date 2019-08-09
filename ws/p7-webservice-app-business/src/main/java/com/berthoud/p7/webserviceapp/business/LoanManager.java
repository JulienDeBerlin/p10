package com.berthoud.p7.webserviceapp.business;

import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.consumer.contract.CustomerDAO;
import com.berthoud.p7.webserviceapp.consumer.contract.LoanDAO;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This class is dedicated to the management of loans.
 */


@Service
@PropertySource("classpath:application.properties")
public class LoanManager {


    @Value("${maxExtensions}")
    private String maxExtensions;

    @Value("${extensionLengthInDays}")
    private String extensionLengthInDays;

    @Value("${loanLengthInDays}")
    private String loanLengthInDays;


    @Autowired
    LoanDAO loanDAO;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    CustomerDAO customerDAO;


    /**
     * The method is used to extend an active loan. The extension of a loan is only possible if all following conditions are met:
     * - membership is still valid
     * - max number of extension has not been reached (value to be set in separate settings.properties file)
     * - and of course, if loan id matches with an existing and still open loan.
     * <p>
     * The length of the extension is also to be set in the separate settings.properties file.
     *
     * @param loanId the id of the loan to be extended
     * @return :
     * 1 = success (loan has been extended),
     * 0 = failure (membership expired),
     * -1 = failure (max amount of extensions reached),
     * -2 = failure (loanId not correct)
     */
    public int extendLoan(int loanId) {
        BusinessLogger.logger.trace("entering method extendLoan with param loanId = " + loanId);

        Optional<Loan> l = loanDAO.findById(loanId);
        if (!l.isPresent() || !l.get().getBook().getStatus().equals(Book.Status.BORROWED)) {
            BusinessLogger.logger.info("failure loan extension, cause: loanId " +loanId +" not correct ");

            return -2;
        }

        Loan loan = l.get();

        if (loan.getCustomer().getDateExpirationMembership().isBefore(LocalDate.now())) {
            BusinessLogger.logger.info("failure loan extension, cause: membership expired for customer with id " + loan.getCustomer().getId());

            return 0;
        }


        if (loan.getNumberExtensions() < Integer.parseInt(maxExtensions)) {
            loan.setDateEnd(loan.getDateEnd().plusDays(Integer.parseInt(extensionLengthInDays)));
            loan.setNumberExtensions(loan.getNumberExtensions() + 1);
            loanDAO.save(loan);
            BusinessLogger.logger.info(" loan extension successfull ");

            return 1;

        } else {
            BusinessLogger.logger.info("failure loan extension, cause: max amount of extensions reached for loan id " + loanId);
            return -1;

        }
    }


    /**
     * This method is used to register a new loan.
     * The registration of a new loan is only possible if all following conditions are met:
     * - membership is still valid
     * - the book is available
     * - id of customer and id of book are correct.
     *
     * @param customerId the id of the customer who want to borrow the book
     * @param bookId     the id of the book wished by the customer
     * @return :
     * 1 = success (loan is possible and registered),
     * 0 = failure (membership expired),
     * -1 = failure (book Id not correct),
     * -2 = failure (customer Id not correct),
     * -3 = failure (book not available)
     */
    public int registerNewLoan(int customerId, int bookId) {
        BusinessLogger.logger.trace("entering method registerNewLoan with param CustomerId= "+ customerId+ " and bookId= " + bookId);

        Optional<Book> b = bookDAO.findById(bookId);
        if (!b.isPresent()) {
            BusinessLogger.logger.info("failure registration new loan, cause: bookId " + bookId+ " not correct ");
            return -1;
        }

        if (!b.get().getStatus().equals(Book.Status.AVAILABLE)) {
            BusinessLogger.logger.info("failure registration new loan, cause: book with id" + bookId+ " not available ");

            return -3;
        }


        Optional<Customer> c = customerDAO.findById(customerId);
        if (!c.isPresent()) {
            BusinessLogger.logger.info("failure registration new loan, cause: customer id " + customerId+ "not correct ");
            return -2;
        }

        if (c.get().getDateExpirationMembership().isBefore(LocalDate.now())) {
            BusinessLogger.logger.info("failure registration new loan, cause: membership expired for customer id " + customerId);

            return 0;
        }

        // create new loan
        Loan newloan = new Loan();
        newloan.setCustomer(c.get());
        newloan.setBook(b.get());
        newloan.setNumberExtensions(0);
        newloan.setDateBegin(LocalDate.now());

        LocalDate DateEnd = LocalDate.now().plusDays(Integer.parseInt(loanLengthInDays));
        newloan.setDateEnd(DateEnd);


        newloan.setDateBack(LocalDate.of(1900, 1, 1));
        loanDAO.save(newloan);

        // change book status
        b.get().setStatus(Book.Status.BORROWED);
        bookDAO.save(b.get());
        BusinessLogger.logger.info("registration new loan successfull");

        return 1;
    }


    /**
     * This method is used to register a new loan.
     *
     * @param bookId the id of the book that is returned.
     * @return :
     * 1 = success (book return has been validated),
     * 0 = failure (no loan active with for this book id)
     * -1 = failure (bookId is not a valid book id)
     */

    public int bookBack(int bookId) {
        BusinessLogger.logger.trace("entering method bookBack with param bookId =" + bookId);

        Optional<Book> b = bookDAO.findById(bookId);
        if (!b.isPresent()) {
            BusinessLogger.logger.info(" failure registration book return, cause: bookId "+bookId + " is not valid ");

            return -1;
        }

        Book book = b.get();
        if (!book.getStatus().equals(Book.Status.BORROWED)) {
            BusinessLogger.logger.info(" failure registration book return, cause: no loan active for book with id "+bookId);

            return 0;
        }

        for (Loan l : book.getLoans()) {

            if (l.getDateBack().equals(LocalDate.of(1900, 1, 1))) {
                l.setDateBack(LocalDate.now());
                loanDAO.save(l);

                book.setStatus(Book.Status.AVAILABLE);
                bookDAO.save(book);

                break;
            }
        }
        BusinessLogger.logger.info(" registration book return, bookId "+bookId + " was successfull");

        return 1;
    }


    /**
     * This method is used for the loan monitoring.
     *
     * @return a list of all still open loans.
     */
    public List<Loan> getAllOpenLoans() {
        BusinessLogger.logger.trace("entering getAllOpenLoans() ");

        List<Loan> allLoans = new ArrayList<>();
        allLoans.addAll(getOpenLoansInTime());
        allLoans.addAll(getOpenLoansLate());
        return allLoans;
    }

    /**
     * This method is used for the loan monitoring.
     *
     * @return a list of the open loans for which the return deadline has not been reached yet.
     */
    public List<Loan> getOpenLoansInTime() {
        BusinessLogger.logger.trace("entering getOpenLoansInTime() ");

        // DateBack is 01/01/1900 for Loan still open
        LocalDate back = LocalDate.of(1900, 01, 01);
        return loanDAO.findByDateBackAndDateEndGreaterThan(back, LocalDate.now());
    }

    /**
     * This method is used for the loan monitoring.
     *
     * @return a list of the open loans for which the return deadline has been reached.
     */
    public List<Loan> getOpenLoansLate() {
        BusinessLogger.logger.trace("entering getOpenLoansLate() ");

        // DateBack is 01/01/1900 for Loan still open
        LocalDate back = LocalDate.of(1900, 01, 01);
        return loanDAO.findByDateBackAndDateEndLessThanEqual(back, LocalDate.now());
    }



    /**
     * This method is used for the loan monitoring.
     *
     * @return a list of the open loans that has already been extended at least once.
     */
    public List<Loan> getOpenLoansExtended(){
        BusinessLogger.logger.trace("entering getOpenLoansExtended()");

        LocalDate back = LocalDate.of(1900, 01, 01);
        return  loanDAO.findByDateBackAndNumberExtensionsGreaterThan(back, 0);
    }

}

