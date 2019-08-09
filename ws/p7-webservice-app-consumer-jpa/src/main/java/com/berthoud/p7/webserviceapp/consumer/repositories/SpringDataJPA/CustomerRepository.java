package com.berthoud.p7.webserviceapp.consumer.repositories.SpringDataJPA;

import com.berthoud.p7.webserviceapp.consumer.contract.CustomerDAO;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>, CustomerDAO {

 List<Customer> findBySurnameIgnoreCase (String surname);
 List<Customer> findByEmailAndPassword(String email, String password);

 // here instead of the findAll in-built method, we use JPQL:
 @Query ("from Customer")
 List <Customer> findAllJPQL();

// here we use a JPQL query to look for customers based on their id, firstName and sex
 @Query ("select ce.id, ce.firstName, ce.sex from Customer ce")
 List <Object []> findAllPartialData();




}
