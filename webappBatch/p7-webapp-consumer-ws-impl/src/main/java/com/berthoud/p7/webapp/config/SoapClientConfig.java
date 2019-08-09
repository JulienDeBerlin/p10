package com.berthoud.p7.webapp.config;

import com.berthoud.p7.webapp.clients.BooksClientWs;
import com.berthoud.p7.webapp.clients.CustomersAndLoansClientWs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


/**
 * config class for webservice client
 */
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
})
public class SoapClientConfig {

    @Value("${URI_CUSTOMERS_AND_LOANS}")
    public String URI_CUSTOMERS_AND_LOANS;

    @Value("${URI_BOOKS}")
    private String URI_BOOKS;


    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the  specified in
        // pom.xml
        marshaller.setContextPath("customersAndLoans.wsdl");
        return marshaller;
    }

    @Bean
    public CustomersAndLoansClientWs customersAndLoansClientWs(Jaxb2Marshaller marshaller) {
        CustomersAndLoansClientWs client = new CustomersAndLoansClientWs();
        client.setDefaultUri(URI_CUSTOMERS_AND_LOANS);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public Jaxb2Marshaller marshaller2() {
        Jaxb2Marshaller marshaller2 = new Jaxb2Marshaller();
        // this package must match the package in the  specified in
        // pom.xml
        marshaller2.setContextPath("books.wsdl");
        return marshaller2;
    }

    @Bean
    public BooksClientWs booksClientWs(Jaxb2Marshaller marshaller2) {
        BooksClientWs client = new BooksClientWs();
        client.setDefaultUri(URI_BOOKS);
        client.setMarshaller(marshaller2);
        client.setUnmarshaller(marshaller2);
        return client;
    }


}
