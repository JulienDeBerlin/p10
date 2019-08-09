package com.berthoud.p7.webapp.consumer.ws.impl;

import com.berthoud.p7.webapp.clients.BooksClientWs;
import com.berthoud.p7.webapp.config.SoapClientConfig;
import com.berthoud.p7.webapp.consumer.contracts.BookReferenceDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;
import p7.webapp.model.beans.BookReference;

import java.util.List;


/**
 * The class is the implementation of the interface {@link BookReferenceDAO}.
 * The implementation is made by using the webservice client class {@link BooksClientWs}
 */
@Repository
public class BookReferenceWsImpl extends AbstractWsImpl implements BookReferenceDAO {

    @Override
    public List<BookReference> getResultBookResearch(String authorSurname, String titleElement, List<String> tags, int librairyId) {
        return booksClientWs.researchBookReferencesMapped(titleElement, authorSurname, tags, librairyId);
    }
}
