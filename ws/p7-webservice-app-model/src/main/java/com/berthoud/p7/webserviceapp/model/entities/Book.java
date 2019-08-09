package com.berthoud.p7.webserviceapp.model.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;



@Entity
public class Book extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private LocalDate datePurchase;

    public enum Status {
        AVAILABLE, BOOKED, BORROWED
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "librairy_id")
    private Librairy librairy;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Loan> loans;

    @ManyToOne
    @JoinColumn(name = "book_reference_id")
    private BookReference bookReference;

    public Book(LocalDate datePurchase, Status status, Librairy librairy, Set<Loan> loans, BookReference bookReference) {
        this.datePurchase = datePurchase;
        this.status = status;
        this.librairy = librairy;
        this.loans = loans;
        this.bookReference = bookReference;
    }

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(LocalDate datePurchase) {
        this.datePurchase = datePurchase;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Librairy getLibrairy() {
        return librairy;
    }

    public void setLibrairy(Librairy librairy) {
        this.librairy = librairy;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    public BookReference getBookReference() {
        return bookReference;
    }

    public void setBookReference(BookReference bookReference) {
        this.bookReference = bookReference;
    }
}




