package com.berthoud.p7.webserviceapp.consumer.contract;

import com.berthoud.p7.webserviceapp.model.entities.Book;

import java.util.Optional;

public interface BookDAO {

    Optional<Book> findById(int bookId);
    Book save(Book book);
}
