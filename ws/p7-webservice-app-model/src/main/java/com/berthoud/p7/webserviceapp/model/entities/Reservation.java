package com.berthoud.p7.webserviceapp.model.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    @JoinColumn(name = "book_reference_id")
    BookReference bookReference;

    @ManyToOne
    @JoinColumn(name = "librairy_id")
    Librairy librairy;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(nullable=false)
    LocalDate dateReservation;

    LocalDate dateBookAvailableNotification;



    public Reservation() {
    }

    public Reservation(BookReference bookReference, Librairy librairy, Customer customer, LocalDate dateReservation, LocalDate dateBookAvailableNotification) {
        this.bookReference = bookReference;
        this.librairy = librairy;
        this.customer = customer;
        this.dateReservation = dateReservation;
        this.dateBookAvailableNotification = dateBookAvailableNotification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookReference getBookReference() {
        return bookReference;
    }

    public void setBookReference(BookReference bookReference) {
        this.bookReference = bookReference;
    }

    public Librairy getLibrairy() {
        return librairy;
    }

    public void setLibrairy(Librairy librairy) {
        this.librairy = librairy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDate getDateBookAvailableNotification() {
        return dateBookAvailableNotification;
    }

    public void setDateBookAvailableNotification(LocalDate dateBookAvailableNotification) {
        this.dateBookAvailableNotification = dateBookAvailableNotification;
    }
}
