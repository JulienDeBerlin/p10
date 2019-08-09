package com.berthoud.p7.webapp.clients;

import books.wsdl.*;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import p7.webapp.model.beans.Book;
import p7.webapp.model.beans.BookReference;
import p7.webapp.model.beans.Librairy;
import p7.webapp.model.beans.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.berthoud.p7.webapp.utils.Utils.convertXmlDateToLocal;

/**
 * This class consumes the webservices offered by the wsdl books.wsdl
 */
public class BooksClientWs extends WebServiceGatewaySupport {

    /**
     * This method is used to perform a book research using a webservice. There are 3 research parameters ( author, title or keywords also called tags)
     * and 1 filter by librairy. At least 1 research parameter should be indicated otherwise an exception is thrown.
     *
     * @param authorSurname research parameters: surname of the author (NOT case sensitive)
     * @param titleElement  research parameters: title or part of it (NOT case sensitive)
     * @param tags          research parameters: list of keywords (NOT case sensitive)
     * @param librairyId    research filter: -1 for all librairies, or librairyId to restrict the search to one librairy
     * @return a Webservice {@link BookResponse} object
     */
    public BookResponse researchBookReferencesWs(String titleElement, String authorSurname, List<String> tags, int librairyId) {
        BookRequest request = new BookRequest();
        request.setAuthorSurname(authorSurname);
        request.setTitleElement(titleElement);
        request.setLibrairyId(librairyId);
        request.getTags().addAll(tags);

        return (BookResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    /**
     * This method is used to perform a book research using a webservice. The result is then mapped into a list of {@link BookReference} objects
     *
     * @param authorSurname research parameters: surname of the author (NOT case sensitive)
     * @param titleElement  research parameters: title or part of it (NOT case sensitive)
     * @param tags          research parameters: list of keywords (NOT case sensitive)
     * @param librairyId    research filter: -1 for all librairies, or librairyId to restrict the search to one librairy
     * @return a list of {@link BookReference} objects
     */
    public List<BookReference> researchBookReferencesMapped(String titleElement, String authorSurname, List<String> tags, int librairyId) {
        BookResponse bookResponseWs = researchBookReferencesWs(titleElement, authorSurname, tags, librairyId);

        List<BookReference> bookReferenceList = new ArrayList<>();


        for (BookReferenceWs bookReferenceWs : bookResponseWs.getBookReferences()) {
            BookReference bookReference = new BookReference();
            BeanUtils.copyProperties(bookReferenceWs, bookReference);

            //Mapping Set<Tag> inside BookReference
            Set<Tag> tagSet = new HashSet<>();
            for (TagsWs tagsWs: bookReferenceWs.getTags()){
                Tag tag = new Tag();
                BeanUtils.copyProperties(tagsWs, tag);
                tagSet.add(tag);
            }

            bookReference.setTags(tagSet);

            // Mapping Set<Book> inside BookReference
            Set<Book> bookSet = new HashSet<>();
            for (BookWs bookWs : bookReferenceWs.getBook()) {
                Book book = new Book();
                BeanUtils.copyProperties(bookWs, book);

                book.setDatePurchase(convertXmlDateToLocal(bookWs.getDatePurchase()));

                switch (bookWs.getStatus()) {
                    case AVAILABLE:
                        book.setStatus(Book.Status.AVAILABLE);
                        break;
                    case BOOKED:
                        book.setStatus(Book.Status.BOOKED);
                        break;
                    case BORROWED:
                        book.setStatus(Book.Status.BORROWED);
                        break;
                }

                Librairy librairy = new Librairy();
                BeanUtils.copyProperties(bookWs.getLibrairy(), librairy);
                book.setLibrairy(librairy);

                bookSet.add(book);
            }

            bookReference.setBooks(bookSet);
            bookReferenceList.add(bookReference);
        }

        return bookReferenceList;
    }

    /**
     * This method is used to retrieve all the available librairies through a webservice.
     *
     * @return a Webservice {@link ListLibrairyResponse} object
     */
    public ListLibrairyResponse getListLibrairies() {
        ListLibrairyRequest request = new ListLibrairyRequest();
        return (ListLibrairyResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }


    /**
     * This method is used to retrieve all the available librairies through a webservice.
     * The result is mapped into a List of {@link Librairy} objects.
     *
     * @return a list of {@link Librairy} objects.
     */
    public List<Librairy> getListLibrairiesMapped() {
        List<Librairy> librairyList = new ArrayList<>();

        List<LibrairyWs> librairyWsList = getListLibrairies().getLibrairyWs();
        for (LibrairyWs librairyWs : librairyWsList) {
            Librairy librairy = new Librairy();
            BeanUtils.copyProperties(librairyWs, librairy);
            librairyList.add(librairy);
        }
        return librairyList;
    }


}


