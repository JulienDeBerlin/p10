package com.berthoud.p7.webserviceapp;

import com.berthoud.p7.webserviceapp.business.BookResearchManager;
import com.berthoud.p7.webserviceapp.consumer.contract.BookDAO;
import com.berthoud.p7.webserviceapp.consumer.contract.BookReferenceDAO;
import com.berthoud.p7.webserviceapp.model.entities.BookReference;
import com.berthoud.p7.webserviceapp.model.entities.Librairy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ITBookResearch {

    @Autowired
    BookReferenceDAO bookReferenceRepository;

    @Autowired
    BookResearchManager bookResearchManager;

    @Autowired
    BookDAO bookRepo;


    // TI bookReferenceRepository
    @Test
    public void findByTags() {
        List<BookReference> bookReferenceList = bookReferenceRepository.findBookReferenceByTags(new HashSet<>(Arrays.asList("sport", "aventure", "ecologie")), 3);
        assertTrue(bookReferenceList.isEmpty());

        bookReferenceList = bookReferenceRepository.findBookReferenceByTags(new HashSet<>(Arrays.asList("italie")), 1);
        assertEquals(bookReferenceList.size(), 2);
    }

    @Test
    public void findByTagsAndAuthor() {

        List<BookReference> bookReferenceList =
                bookReferenceRepository.findBookReferenceByTagsAndAuthor(new HashSet<>(Arrays.asList("histoire")), 1, "delors");
        assertEquals(bookReferenceList.size(), 1);
        assertEquals(bookReferenceList.get(0).getTitle(), "Mon Italie");
    }

    @Test
    public void findByTagsAndTitleElement() {

        List<BookReference> bookReferenceList =
                bookReferenceRepository.findBookReferenceByTagsAndTitleElement(new HashSet<>(Arrays.asList("histoire")), 1, "Italie");
        assertEquals(bookReferenceList.size(), 1);
        assertEquals(bookReferenceList.get(0).getTitle(), "Mon Italie");
    }

    @Test
    public void findByTagsAndTitleElementAndAuthor() {
        List<BookReference> bookReferenceList =
                bookReferenceRepository.findBookReferenceByTagsAndTitleElementAndAuthor
                        (new HashSet<>(Arrays.asList("sport", "aventure", "écologie")), 3, "les septs", "sur");
        assertEquals(bookReferenceList.size(), 1);
        assertEquals(bookReferenceList.get(0).getTitle(), "Les septs collines en Italie");
    }

    // TI bookResearchManager
    @Test
    public void findMultipleParameters() {
        List<BookReference> bookReferenceList10 =
                bookResearchManager.findBookMultiParameters("Sur", "", -1, Arrays.asList("sport"));
        assertEquals(bookReferenceList10.size(), 1);

        List<BookReference> bookReferenceLis11 =
                bookResearchManager.findBookMultiParameters("", "Italie", -1, Arrays.asList("sport"));
        assertEquals(bookReferenceLis11.size(), 1);

        List<BookReference> bookReferenceList =
                bookResearchManager.findBookMultiParameters("Sur", "Italie", -1, Arrays.asList("sport", "aventure", "écologie"));
        assertEquals(bookReferenceList.size(), 1);

        List<BookReference> bookReferenceList2 =
                bookResearchManager.findBookMultiParameters("Sur", "Italie", 1, Arrays.asList("sport", "aventure", "écologie"));
        assertEquals(bookReferenceList2.size(), 1);

        List<BookReference> bookReferenceList3 =
                bookResearchManager.findBookMultiParameters("Sur", "iTtalie", 2, Arrays.asList("sport", "aventure", "écologie"));
        assertEquals(bookReferenceList3.size(), 0);

        List<BookReference> bookReferenceList4 =
                bookResearchManager.findBookMultiParameters("sur", "Italie", -1, Arrays.asList("sport", "aventure", "écologie"));
        assertEquals(bookReferenceList4.size(), 1);

        List<BookReference> bookReferenceList5 =
                bookResearchManager.findBookMultiParameters("Sur", "Ital", -1, Arrays.asList("sport", "aventure", "écologie"));
        assertEquals(bookReferenceList5.size(), 1);

        List<BookReference> bookReferenceList6 =
                bookResearchManager.findBookMultiParameters("Su", "Italie", -1, Arrays.asList("sport", "aventure", "écologie"));
        assertEquals(bookReferenceList6.size(), 0);

        List<BookReference> bookReferenceList7 =
                bookResearchManager.findBookMultiParameters("Sur", "Italie", -1, Arrays.asList("sport", "écologie", "écologie"));
        assertEquals(bookReferenceList7.size(), 1);

        List<BookReference> bookReferenceList8 =
                bookResearchManager.findBookMultiParameters("", "Italie", -1, Arrays.asList());
        assertEquals(bookReferenceList8.size(), 3);

        List<BookReference> bookReferenceList9 =
                bookResearchManager.findBookMultiParameters("", "", -1, Arrays.asList("architecture"));
        assertEquals(bookReferenceList9.size(), 2);
    }

    @Test
    public void findAllLibrairies(){
        List<Librairy> librairyList = bookResearchManager.getAllLibrairies();
        assertEquals(librairyList.size(), 3);
    }
}
