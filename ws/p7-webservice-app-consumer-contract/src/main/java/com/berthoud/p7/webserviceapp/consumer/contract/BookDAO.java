package com.berthoud.p7.webserviceapp.consumer.contract;

import com.berthoud.p7.webserviceapp.model.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {

    Optional<Book> findById(int id);
    Book save(Book book);

    List<Book> getListOfBooksForReferenceAndLibrairy(int bookReferenceId, int librairyId);
}
