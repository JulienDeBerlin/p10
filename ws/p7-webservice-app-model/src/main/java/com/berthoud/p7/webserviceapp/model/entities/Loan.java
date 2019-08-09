package com.berthoud.p7.webserviceapp.model.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan extends AuditModel {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private LocalDate dateBegin;

    @Column(nullable = false)
    private LocalDate dateEnd;

    @Column(nullable = false)
    private int numberExtensions;

    @Column(nullable = false)
    private LocalDate dateBack;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Loan(LocalDate dateBegin, LocalDate dateEnd, int numberExtensions, LocalDate dateBack, Customer customer, Book book) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.numberExtensions = numberExtensions;
        this.dateBack = dateBack;
        this.customer = customer;
        this.book = book;
    }

    public Loan() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getNumberExtensions() {
        return numberExtensions;
    }

    public void setNumberExtensions(int numberExtensions) {
        this.numberExtensions = numberExtensions;
    }

    public LocalDate getDateBack() {
        return dateBack;
    }

    public void setDateBack(LocalDate dateBack) {
        this.dateBack = dateBack;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
