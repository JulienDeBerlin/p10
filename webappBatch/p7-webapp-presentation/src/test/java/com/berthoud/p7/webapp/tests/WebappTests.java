package com.berthoud.p7.webapp.tests;

import com.berthoud.p7.webapp.business.managers.BookResearchManager;
import com.berthoud.p7.webapp.clients.BooksClientWs;
import com.berthoud.p7.webapp.clients.CustomersAndLoansClientWs;
import com.berthoud.p7.webapp.config.SoapClientConfig;
import customersAndLoans.wsdl.LoginCustomerResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.ws.soap.client.SoapFaultClientException;
import p7.webapp.model.beans.BookReference;
import p7.webapp.model.beans.Librairy;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WebappTests {

    @Autowired
    BookResearchManager bookResearchManager;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testLogin() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
        CustomersAndLoansClientWs client = context.getBean(CustomersAndLoansClientWs.class);

        try {
            LoginCustomerResponse response = client.getCustomerWs("malika@yahoo.fr", "soleiel");

            System.out.println("Prénom = " + response.getCustomer().getFirstName() + "\n");
            System.out.println("Nom = " + response.getCustomer().getSurname() + "\n");
            System.out.println("Email = " + response.getCustomer().getEmail() + "\n");

            response.getCustomer().getLoans().forEach(loanWs -> System.out.println(loanWs.getBook().getBookReference().getTitle()));

        } catch (SoapFaultClientException e) {

            e.getSoapFault().getFaultDetail();





            System.out.println(e.getMessage());
            }

    }


    @Test
    public void getLibrairies() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
        BooksClientWs client = context.getBean(BooksClientWs.class);

        List<Librairy> librairyList = client.getListLibrairiesMapped();
        assertEquals(librairyList.size(), 3);

        librairyList = bookResearchManager.getAllLibrairies();
        assertEquals(librairyList.size(), 3);

    }

    @Test
    public void convertTagsStringIntoList() {

        String tagsAsString = "Architecture, Cheval, Poésie";
        List<String> tagsAsList = bookResearchManager.convertTagsIntoList(tagsAsString);
        assertEquals(tagsAsList.size(), 3);
        assertEquals(tagsAsList.get(1), "cheval");
        assertEquals(tagsAsList.get(0), "architecture");
        assertEquals(tagsAsList.get(2), "poésie");

    }


    @Test
    public void findMultipleParameters() {

        List<BookReference> bookReferenceList10 =
                bookResearchManager.getResultBookResearch("Sur", "", Arrays.asList("sport"), -1);
        assertEquals(bookReferenceList10.size(), 1);

        List<BookReference> bookReferenceLis11 =
                bookResearchManager.getResultBookResearch("", "Italie", Arrays.asList("sport"), -1);
        assertEquals(bookReferenceLis11.size(), 1);

        List<BookReference> bookReferenceList =
                bookResearchManager.getResultBookResearch("Sur", "Italie", Arrays.asList("sport", "aventure", "écologie"), -1);
        assertEquals(bookReferenceList.size(), 1);

        List<BookReference> bookReferenceList2 =
                bookResearchManager.getResultBookResearch("Sur", "Italie", Arrays.asList("sport", "aventure", "écologie"), -1);
        assertEquals(bookReferenceList2.size(), 1);

        List<BookReference> bookReferenceList3 =
                bookResearchManager.getResultBookResearch("Sur", "Italie", Arrays.asList("sport", "aventure", "écologie"), 2);
        assertEquals(bookReferenceList3.size(), 0);

        List<BookReference> bookReferenceList4 =
                bookResearchManager.getResultBookResearch("sur", "Italie", Arrays.asList("sport", "aventure", "écologie"), -1);
        assertEquals(bookReferenceList4.size(), 1);

        List<BookReference> bookReferenceList5 =
                bookResearchManager.getResultBookResearch("Sur", "Ital", Arrays.asList("sport", "aventure", "écologie"), -1);
        assertEquals(bookReferenceList5.size(), 1);

        List<BookReference> bookReferenceList6 =
                bookResearchManager.getResultBookResearch("Su", "Italie", Arrays.asList("sport", "aventure", "écologie"), -1);
        assertEquals(bookReferenceList6.size(), 0);

        List<BookReference> bookReferenceList7 =
                bookResearchManager.getResultBookResearch("Sur", "Italie", Arrays.asList("sport", "écologie", "écologie"), -1);
        assertEquals(bookReferenceList7.size(), 1);

        List<BookReference> bookReferenceList8 =
                bookResearchManager.getResultBookResearch("", "Italie", Arrays.asList(), -1);
        assertEquals(bookReferenceList8.size(), 3);

        List<BookReference> bookReferenceList9 =
                bookResearchManager.getResultBookResearch("", "", Arrays.asList("architecture"), -1);
        assertEquals(bookReferenceList9.size(), 2);

    }


}