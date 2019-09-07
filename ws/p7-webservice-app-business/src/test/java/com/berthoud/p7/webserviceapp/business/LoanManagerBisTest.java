//package com.berthoud.p7.webserviceapp.business;
//
//import com.berthoud.p7.webserviceapp.consumer.contract.LoanDAO;
//import com.berthoud.p7.webserviceapp.model.entities.Book;
//import com.berthoud.p7.webserviceapp.model.entities.Customer;
//import com.berthoud.p7.webserviceapp.model.entities.Loan;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestContextConfiguration.class)
//@PropertySource("classpath:application.properties")
//
//public class LoanManagerBisTest {
//
//    @Mock
//    private LoanDAO loanDaoMock;
//
//    @InjectMocks
//    @Autowired
//    private LoanManager loanManager;
//
//    @Value("${maxExtensions}")
//    private int maxExtensions;
//
//    @Value("${extensionLengthInDays}")
//    private int extensionLengthInDays;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//
//    @Test
//    public void extendLoanTest() {
//
//        Customer customerMembershipValid = new Customer();
//        customerMembershipValid.setDateExpirationMembership(LocalDate.now().plusDays(10));
//
//        Customer customerMembershipExpired = new Customer();
//        customerMembershipExpired.setDateExpirationMembership(LocalDate.now().minusDays(10));
//
//        // LoanId 1 = open loan, number of extension limit not reached, membership not expired
//        Loan openLoan1 = new Loan();
//        openLoan1.setId(1);
//        openLoan1.setDateBack(LocalDate.of(1900, 01, 01));
//        LocalDate dateEndBeforeExtension1 = LocalDate.of(2021, 03, 01);
//        openLoan1.setDateEnd(dateEndBeforeExtension1);
//        openLoan1.setNumberExtensions(maxExtensions - 1);
//        openLoan1.setCustomer(customerMembershipValid);
//
//        Book book1 = new Book();
//        book1.setStatus(Book.Status.BORROWED);
//        openLoan1.setBook(book1);
//
//        Optional<Loan> opt = Optional.of(openLoan1);
//
//        when(loanDaoMock.findById(1)).thenReturn(opt);
//        assertEquals(loanManager.extendLoan(1), 1);
//        assertEquals(openLoan1.getDateEnd(), dateEndBeforeExtension1.plusDays(extensionLengthInDays));
//
//
////        // LoanId 2 = open loan, number of extension limit  reached
////        Loan openLoan2 = new Loan();
////        openLoan1.setId(1);
////        openLoan1.setDateBack(LocalDate.of(1900, 01, 01));
////        LocalDate dateEndBeforeExtension2 = LocalDate.of(2021, 03, 01);
////        openLoan1.setDateEnd(dateEndBeforeExtension2);
////        openLoan1.setNumberExtensions(maxLoanExtension);
////
////        when(loanDaoMock.findById(2)).thenReturn(Optional.of(openLoan2));
////        loanManager.extendLoan(2);
////        assertEquals (openLoan1.getDateEnd(), dateEndBeforeExtension2.plusDays(extensionLengthInDays));
////
////
////
////
////
////        // LoanId 2 = closed loan
////
////        Loan closedLoan = new Loan();
////        openLoan1.setId(2);
////        closedLoan.setDateBack(LocalDate.of(2019, 05, 17));
////        closedLoan.setDateEnd(LocalDate.of(2019, 05, 20));
////        when(loanDaoMock.findById(2)).thenReturn(Optional.of(closedLoan));
////
////        // LoanId 3 = non existant loan
////        when(loanDaoMock.findById(3)).thenReturn((Optional.empty()));
//
//
//    }
//}
