package com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA;

import com.berthoud.p7.webserviceapp.consumer.contract.LoanDAO;
import com.berthoud.p7.webserviceapp.model.entities.Loan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface LoanRepository extends CrudRepository <Loan, Integer>, LoanDAO {

    @Override
    Loan save(Loan loan);

    List<Loan> findByNumberExtensionsGreaterThan(int amount);

}
