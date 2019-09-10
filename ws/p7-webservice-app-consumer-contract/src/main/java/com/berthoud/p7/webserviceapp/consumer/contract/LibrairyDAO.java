package com.berthoud.p7.webserviceapp.consumer.contract;

import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Librairy;

import java.util.List;
import java.util.Optional;

public interface LibrairyDAO {

    List<Librairy> findAll();
    Optional<Librairy> findById(int id);
}
