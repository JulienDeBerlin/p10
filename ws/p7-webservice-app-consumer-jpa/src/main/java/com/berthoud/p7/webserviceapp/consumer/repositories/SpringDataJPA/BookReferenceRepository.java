package com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA;

import com.berthoud.p7.webserviceapp.consumer.contract.BookReferenceDAO;
import com.berthoud.p7.webserviceapp.model.entities.BookReference;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BookReferenceRepository extends CrudRepository<BookReference, Integer>, BookReferenceDAO {


    @Query( "select b from BookReference b where :numberOfTags = (select count(tag.id)" +
            " from BookReference b2 inner join b2.tags tag where b2.id = b.id and tag.name IN (:tags)) "
            )
    List<BookReference> findBookReferenceByTags(@Param (value = "tags") Set<String> tags,
                                                @Param(value = "numberOfTags") long numberOfTags);


    @Query( "select b from BookReference b where :numberOfTags = (select count(tag.id)" +
            " from BookReference b2 inner join b2.tags tag where b2.id = b.id and tag.name IN (:tags)) " +
            "AND lower(b.authorSurname) = lower(:authorSurname)" )
    List<BookReference> findBookReferenceByTagsAndAuthor(@Param (value = "tags") Set<String> tags,
                                                        @Param(value = "numberOfTags") long numberOfTags,
                                                         @Param (value = "authorSurname") String authorSurname);


    @Query( "select b from BookReference b where :numberOfTags = (select count(tag.id)" +
            " from BookReference b2 inner join b2.tags tag where b2.id = b.id and tag.name IN (:tags)) " +
            "AND lower(b.title) like  concat( '%', lower(:titleElement), '%' )" )
    List<BookReference> findBookReferenceByTagsAndTitleElement(@Param(value = "tags") Set<String> tags,
                                                         @Param(value = "numberOfTags") long numberOfTags,
                                                         @Param (value = "titleElement") String titleElement);


    @Query( "select b from BookReference b where :numberOfTags = (select count(tag.id)" +
            " from BookReference b2 inner join b2.tags tag where b2.id = b.id and tag.name IN ( :tags)) " +
            "AND lower(b.title) like  concat( '%', lower(:titleElement), '%' )"+
            " AND lower(b.authorSurname) like lower(:authorSurname)")
    List<BookReference> findBookReferenceByTagsAndTitleElementAndAuthor(@Param (value = "tags") Set<String> tags,
                                                               @Param(value = "numberOfTags") long numberOfTags,
                                                               @Param (value = "titleElement") String titleElement,
                                                                @Param (value = "authorSurname") String authorSurname);


    @Query("select b from BookReference b where b.authorSurname =:author")
    List<BookReference> findBookReferenceTestJPQL(@Param (value = "author") String authorSurname );



}

