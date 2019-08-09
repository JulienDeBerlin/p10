package com.berthoud.p7.webapp.consumer.ws.impl;

import com.berthoud.p7.webapp.clients.BooksClientWs;
import com.berthoud.p7.webapp.consumer.contracts.LibrairyDAO;
import org.springframework.stereotype.Repository;
import p7.webapp.model.beans.Librairy;

import java.util.List;

/**
 * The class is the implementation of the interface {@link LibrairyDAO}.
 * The implementation is made by using the webservice client class {@link BooksClientWs}
 */
@Repository
public class LibrairyWsImpl  extends AbstractWsImpl implements LibrairyDAO {


    @Override
    public List<Librairy> findAllLibrairy() {
        return booksClientWs.getListLibrairiesMapped();
    }

}
