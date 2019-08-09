package com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA;

import com.berthoud.p7.webserviceapp.consumer.contract.CustomerDAO;
import com.berthoud.p7.webserviceapp.consumer.contract.LibrairyDAO;
import com.berthoud.p7.webserviceapp.model.entities.Librairy;
import org.springframework.data.repository.CrudRepository;

public interface LibrairyRepository extends CrudRepository<Librairy, Integer>, LibrairyDAO {
}
