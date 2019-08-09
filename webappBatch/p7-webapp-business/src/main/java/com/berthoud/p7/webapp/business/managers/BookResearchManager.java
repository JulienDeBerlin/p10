package com.berthoud.p7.webapp.business.managers;

import com.berthoud.p7.webapp.business.utils.Utils;
import com.berthoud.p7.webapp.consumer.contracts.BookReferenceDAO;
import com.berthoud.p7.webapp.consumer.contracts.LibrairyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p7.webapp.model.beans.*;

import java.util.*;

@Service
public class BookResearchManager {

    @Autowired
    LibrairyDAO librairyDAO;

    @Autowired
    BookReferenceDAO bookReferenceDAO;


    /**
     * @return The method retrieves all librairies available.
     */
    public List<Librairy> getAllLibrairies() {
        Utils.loggerWebappBusiness.trace("entering getAllLibrairies()");

        return librairyDAO.findAllLibrairy();
    }

    /**
     * This method takes as input a list of tags formated as a single String and converts it into a real List of String object
     *
     * @param tagsAsString A string made of one or many tags. The tags are separated by ", " (comma + space).
     * @return a list of String, where each string is the name of the tag.
     */
    public List<String> convertTagsIntoList(String tagsAsString) {
        Utils.loggerWebappBusiness.trace("entering method convertTagsIntoList");


        tagsAsString = tagsAsString.toLowerCase();
        List<String> tagList = new ArrayList<>();

        if (tagsAsString.indexOf(',') != -1) {
            while (tagsAsString.indexOf(',') != -1) {
                String additionalTag = tagsAsString.substring(0, tagsAsString.indexOf(','));
                tagList.add(additionalTag);
                tagsAsString = tagsAsString.substring(tagsAsString.indexOf(',') + 2);
            }
        }
        tagList.add(tagsAsString);
        return tagList;
    }

    /**
     * This method takes as input a set of {@link Tag} objects and returns a single String made of all the tags.
     *
     * @param tagSet A set of {@link Tag} objects
     * @return A string made of one or many tags. The tags are separated by ", " (comma + space).
     */
    public String convertTagsSetIntoString(Set<Tag> tagSet) {
        Utils.loggerWebappBusiness.trace("entering method convertTagsSetIntoString");


        String tagsAsString = "";

        if (tagSet != null) {
            for (Tag tag : tagSet) {
                tagsAsString += tag.getName() + " ";
            }
//            tagsAsString = tagsAsString.substring(0, tagsAsString.lastIndexOf(','));

        } else {
            tagsAsString = "";
        }

        return tagsAsString;
    }


    /**
     * This method is used to perform a book research. There are 3 research parameters (author, title or keywords also called tags)
     * and 1 filter by librairy. At least 1 research parameter should be indicated otherwise an exception is thrown.
     *
     * @param authorSurname research parameters: surname of the author (NOT case sensitive)
     * @param titleElement  research parameters: title or part of it (NOT case sensitive)
     * @param tags          research parameters: list of keywords (NOT case sensitive)
     * @param librairyId    research filter: -1 for all librairies, or librairyId to restrict the search to one librairy
     * @return A list of BookReference objects, matching with the research parameters and the filter
     */
    public List<BookReference> getResultBookResearch(String authorSurname, String titleElement, List<String> tags, int librairyId) {
        Utils.loggerWebappBusiness.trace("entering method getResultBookResearch with param authorSurname = " + authorSurname + " , titleElement= " +titleElement+ " , librairyId= " + librairyId);
        for (String tag:tags) {
        }
        Utils.loggerWebappBusiness.trace("tag= " +tags);


        return bookReferenceDAO.getResultBookResearch(authorSurname, titleElement, tags, librairyId);
    }

    /**
     * This method is used to set for each {@link BookReference} object of List the total number of {@link Book} available (NOT librairy specific!)
     *
     * @param inputList a List of {@link BookReference} objects
     * @return the list completed.
     */
    public List<BookReference> getAmountAvailableBooks(List<BookReference> inputList) {
        Utils.loggerWebappBusiness.trace("entering method getAmountAvailableBooks");

        for (BookReference br : inputList) {
            int amountAvailableBook = 0;

            for (Book b : br.getBooks()) {
                if (b.getStatus().equals(Book.Status.AVAILABLE)) {
                    amountAvailableBook++;
                }
            }
            br.setAmountAvailableBooks(amountAvailableBook);
        }
        return inputList;
    }

    /**
     * This method is used to determine how many {@link Book} of a specific {@link BookReference} are 1/owned and 2/currently available for loan
     * in each librairy.
     *
     * @param bookReference a {@link BookReference} object
     * @return a {@link BookReference} object with ist attribute {@link Availability} set.
     */
    public BookReference getAvailabilitiesEachLibrairy(BookReference bookReference) {
        Utils.loggerWebappBusiness.trace("entering method getAvailabilitiesEachLibrairy with bookReference id = " + bookReference.getId());


        List<Integer> ownedBy = new ArrayList<>();
        List<Integer> availableIn = new ArrayList<>();

        for (Book br : bookReference.getBooks()) {
            ownedBy.add(br.getLibrairy().getId());
            if (br.getStatus().equals(Book.Status.AVAILABLE)) {
                availableIn.add(br.getLibrairy().getId());
            }
        }

        List<Availability> availabilities = new ArrayList<>();
        bookReference.setAvailabilities(availabilities);


        List<Librairy> librairyList = getAllLibrairies();
        for (Librairy l : librairyList) {
            Availability availability = new Availability();
            availability.setLibrairyName(l.getName());
            availability.setAmountBooks(Collections.frequency(ownedBy, l.getId()));
            availability.setAmountAvailableBooks(Collections.frequency(availableIn, l.getId()));
            bookReference.getAvailabilities().add(availability);
        }

        return bookReference;
    }

    /**
     * This method is used to retrieve a {@link BookReference} out of a list.
     *
     * @param bookReferenceId   the id of the {@link BookReference} to be retrieved
     * @param bookReferenceList the list to be searched in
     * @return the selected {@link BookReference} object
     */
    public BookReference getSelectedBookReference(int bookReferenceId, List<BookReference> bookReferenceList) {
        Utils.loggerWebappBusiness.trace("entering method getSelectedBookReference for bookReferenceId= " + bookReferenceId);

        BookReference selectedBookReference = new BookReference();

        Iterator<BookReference> iterator = bookReferenceList.iterator();
        while (iterator.hasNext()) {
            BookReference bookReference = iterator.next();
            if (bookReference.getId() == bookReferenceId) {
                selectedBookReference = bookReference;
                break;
            }
        }
        return selectedBookReference;
    }


}
