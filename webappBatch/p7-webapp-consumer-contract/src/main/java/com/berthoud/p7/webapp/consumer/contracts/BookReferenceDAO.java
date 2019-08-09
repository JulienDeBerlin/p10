package com.berthoud.p7.webapp.consumer.contracts;

import p7.webapp.model.beans.BookReference;

import java.util.List;

public interface BookReferenceDAO {


    List<BookReference> getResultBookResearch( String authorSurname, String titleElement, List <String> tags, int librairyId);
}
