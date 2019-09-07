package com.berthoud.p7.webserviceapp.business;

import com.berthoud.p7.webserviceapp.model.entities.Book;
import com.berthoud.p7.webserviceapp.model.entities.BookReference;
import com.berthoud.p7.webserviceapp.model.entities.Librairy;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BookResearchManagerTest {

    private static BookResearchManager bookResearchManager = new BookResearchManager();

    @Test
    public void filterBookReferenceListByLibrairy(){

        Librairy librairy1 = new Librairy();
        librairy1.setId(1);
        Librairy librairy2 = new Librairy();
        librairy2.setId(2);

        Book bookLibrairy1_Ref1_exemplaire1 = new Book();
        Book bookLibrairy1_Ref1_exemplaire2 = new Book();
        Book bookLibrairy2_Ref2_exemplaire1  = new Book();
        Book bookLibrairy2_Ref3_exemplaire1  = new Book();

        bookLibrairy1_Ref1_exemplaire1.setLibrairy(librairy1);
        bookLibrairy1_Ref1_exemplaire2.setLibrairy(librairy1);
        bookLibrairy2_Ref2_exemplaire1.setLibrairy(librairy2);
        bookLibrairy2_Ref3_exemplaire1.setLibrairy(librairy2);

        BookReference bookReference1 = new BookReference();
        bookReference1.setId(1);
        bookReference1.setAuthorFirstName("Virgina");
        bookReference1.setAuthorSurname("Woolf");
        bookReference1.setTitle("Les vagues");
        Set<Book> booksForRef1 = new HashSet<>();
        bookReference1.setBooks(booksForRef1);
        bookReference1.getBooks().add(bookLibrairy1_Ref1_exemplaire1);
        bookReference1.getBooks().add(bookLibrairy1_Ref1_exemplaire2);

        BookReference bookReference2 = new BookReference();
        bookReference2.setId(2);
        bookReference1.setAuthorFirstName("Virgina");
        bookReference1.setAuthorSurname("Woolf");
        bookReference1.setTitle("Ms Dalloway");
        Set<Book> booksForRef2 = new HashSet<>();
        bookReference2.setBooks(booksForRef2);
        bookReference2.getBooks().add(bookLibrairy2_Ref2_exemplaire1);

        BookReference bookReference3 = new BookReference();
        bookReference3.setId(3);
        bookReference1.setAuthorFirstName("Virgina");
        bookReference1.setAuthorSurname("Woolf");
        bookReference1.setTitle("Orlando");
        Set<Book> booksForRef3 = new HashSet<>();
        bookReference3.setBooks(booksForRef3);
        bookReference3.getBooks().add(bookLibrairy2_Ref3_exemplaire1);

        List<BookReference> bookReferenceListToBeFiltered = new ArrayList<>();
        bookReferenceListToBeFiltered.add(bookReference1);
        bookReferenceListToBeFiltered.add(bookReference2);
        bookReferenceListToBeFiltered.add(bookReference3);

        List<BookReference> bookReferencesFilteredForLibrairy2 = bookResearchManager.filterBookReferenceListByLibrairy(bookReferenceListToBeFiltered,2);
        assertEquals( 2, bookReferencesFilteredForLibrairy2.size());
        bookReferencesFilteredForLibrairy2.add(bookReference1);

        List<BookReference> bookReferencesFilteredForLibrairy1 = bookResearchManager.filterBookReferenceListByLibrairy(bookReferenceListToBeFiltered,1);
        assertEquals( 1, bookReferencesFilteredForLibrairy1.size());
        bookReferencesFilteredForLibrairy2.add(bookReference2);
        bookReferencesFilteredForLibrairy2.add(bookReference3);

        //FIXME : la méthode filterBookReferenceListByLibrairy modifie l'objet rentré en paramètre. Est-ce que c'est ok. Est-ce que ce ne serait
        // pas préférable de copier l'objet passer en paramètre, de modifier la copie et retourner la copie. L'avantage serait que l'original reste inchangé?

    }


}
