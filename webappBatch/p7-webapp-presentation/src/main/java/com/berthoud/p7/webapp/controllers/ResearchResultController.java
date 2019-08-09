package com.berthoud.p7.webapp.controllers;

import com.berthoud.p7.webapp.business.managers.BookResearchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import p7.webapp.model.beans.BookReference;

import java.util.ArrayList;
import java.util.List;

@SessionAttributes(value = "bookReferenceList")
@Controller
public class ResearchResultController {

    @Autowired
    BookResearchManager bookResearchManager;

    /**
     * This controller is used to perform the book research
     *
     * @param model         ---
     * @param authorSurname research parameters: surname of the author
     * @param titleElement  research parameters: title or part of it
     * @param tagsAsString  research parameters: list of keywords as a single String
     * @param librairyId    research filter: -1 for all librairies, or librairyId to restrict the search to one librairy
     * @return the result overview page
     */
    @RequestMapping(value = "/researchResult", method = RequestMethod.POST)
    public String getResult(ModelMap model,
                            @RequestParam(value = "author") String authorSurname,
                            @RequestParam(value = "title") String titleElement,
                            @RequestParam(value = "tags") String tagsAsString,
                            @RequestParam(value = "librairy") int librairyId) {

        if (authorSurname == "" && titleElement == "" && tagsAsString == "") {
            model.addAttribute("alert", "no parameter");
            model.addAttribute("toBeDisplayed", "researchForm");
            return "home";

        } else {

            List<String> tagList = new ArrayList<>();
            if (!tagsAsString.isEmpty()) {
                tagList = bookResearchManager.convertTagsIntoList(tagsAsString);
            }
            List<BookReference> bookReferenceList = bookResearchManager.getResultBookResearch(authorSurname, titleElement, tagList, librairyId);
            bookReferenceList = bookResearchManager.getAmountAvailableBooks(bookReferenceList);

            model.addAttribute("bookReferenceList", bookReferenceList);

            return "researchResultOverview";
        }
    }

    /**
     * This controller is used to display the details of a single {@link BookReference}, selected among the BookRefernce
     * displayed in the result-overview-page.
     *
     * @param model             ---
     * @param bookReferenceId   the id of the BookReference which details should be displayed.
     * @param bookReferenceList the list of BookReference displayed in the result-overview-page.
     * @return the details-page for the selected BookReference.
     */
    @RequestMapping(value = "/bookDetails", method = RequestMethod.GET)
    public String getBookDetails(ModelMap model,
                                 @RequestParam(value = "bookRef") int bookReferenceId,
                                 @SessionAttribute(value = "bookReferenceList") List<BookReference> bookReferenceList) {

        BookReference selectedBookReference = bookResearchManager.getSelectedBookReference(bookReferenceId, bookReferenceList);
        selectedBookReference.setTagsAsString(bookResearchManager.convertTagsSetIntoString(selectedBookReference.getTags()));
        selectedBookReference = bookResearchManager.getAvailabilitiesEachLibrairy(selectedBookReference);
        model.addAttribute("selectedBookReference", selectedBookReference);

        return "researchResultDetails";
    }

    /**
     * This controller is used to go back from a details-page of a specific BookReference to the initial result-overview-page.
     *
     * @return back to the result-overview-page
     */
    @RequestMapping(value = "/backToResultOverview")
    public String backToResultOverview() {
        return "researchResultOverview";
    }

}
