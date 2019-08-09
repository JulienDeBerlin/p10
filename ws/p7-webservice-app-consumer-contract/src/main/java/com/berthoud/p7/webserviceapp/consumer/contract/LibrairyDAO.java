package com.berthoud.p7.webserviceapp.consumer.contract;

import com.berthoud.p7.webserviceapp.model.entities.Librairy;

import java.util.List;

public interface LibrairyDAO {

    List<Librairy> findAll();
}
