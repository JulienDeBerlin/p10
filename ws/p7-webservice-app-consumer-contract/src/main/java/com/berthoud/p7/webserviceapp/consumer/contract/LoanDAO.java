package com.berthoud.p7.webserviceapp.consumer.contract;

import com.berthoud.p7.webserviceapp.model.entities.Loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanDAO {

    Optional<Loan> findById (int loanId);

    Loan save(Loan loan);

    List<Loan> findByDateBackAndDateEndGreaterThan (LocalDate back, LocalDate now );

    List<Loan> findByDateBackAndDateEndLessThanEqual (LocalDate back, LocalDate now );

    List<Loan> findByDateBackAndNumberExtensionsGreaterThan(LocalDate back, int amount);

}
