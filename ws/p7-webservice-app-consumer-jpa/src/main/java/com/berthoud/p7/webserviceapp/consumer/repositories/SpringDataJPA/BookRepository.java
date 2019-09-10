package com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA;

import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.model.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookRepository extends CrudRepository<Book, Integer>, BookDAO {

    @Override
    Book save(Book book);

    @Query("select b FROM Book b JOIN b.bookReference bookRef JOIN b.librairy lib  where bookRef.id=:bookReferenceId and lib.id =:librairyId ")
    List<Book> getListOfBooksForReferenceAndLibrairy(@Param(value = "bookReferenceId") int bookReferenceId,
                                                     @Param(value = "librairyId")int librairyId);


}
