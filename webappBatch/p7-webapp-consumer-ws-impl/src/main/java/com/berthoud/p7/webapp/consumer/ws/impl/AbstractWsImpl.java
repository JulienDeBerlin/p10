package com.berthoud.p7.webapp.consumer.ws.impl;

import com.berthoud.p7.webapp.clients.BooksClientWs;
import com.berthoud.p7.webapp.clients.CustomersAndLoansClientWs;
import com.berthoud.p7.webapp.config.SoapClientConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class  AbstractWsImpl {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
    BooksClientWs booksClientWs =context.getBean(BooksClientWs.class);
    CustomersAndLoansClientWs customersAndLoansClientWs = context.getBean(CustomersAndLoansClientWs.class);


}

