package com.berthoud.p7.webapp.consumer.contracts;


import p7.webapp.model.beans.Librairy;

import java.util.List;

public interface LibrairyDAO {

    List<Librairy> findAllLibrairy ();
}
