package com.berthoud.p7.webserviceapp.consumer.contract;

import com.berthoud.p7.webserviceapp.model.entities.BookReference;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookReferenceDAO {

    List<BookReference> findByTitleContainsIgnoreCase(String titleElement);

    List<BookReference> findByAuthorSurnameIgnoreCase(String AuthorSurname);

    List<BookReference> findByAuthorSurnameAndTitleContainsAllIgnoreCase (String authorSurname, String titleElement);

    List<BookReference> findBookReferenceByTags( Set<String> tags, long numberOfTags);

    List<BookReference> findBookReferenceByTagsAndAuthor(Set<String> tags, long numberOfTags, String authorSurname);

    List<BookReference> findBookReferenceByTagsAndTitleElement(Set<String> tags, long numberOfTags, String titleElement);

    List<BookReference> findBookReferenceByTagsAndTitleElementAndAuthor(Set<String> tags, long numberOfTags, String titleElement, String authorSurname);

    Optional<BookReference> findById (int id);

    List<BookReference> findBookReferenceTestJPQL(String authorSurname );














}
