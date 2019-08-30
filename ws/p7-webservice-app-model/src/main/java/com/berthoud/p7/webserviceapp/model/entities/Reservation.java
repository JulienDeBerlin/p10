package com.berthoud.p7.webserviceapp.model.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_reference_id")
    private BookReference bookReference;

    @ManyToOne
    @JoinColumn(name = "librairy_id")
    private Librairy librairy;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable=false)
    private LocalDateTime dateReservation;

    private LocalDateTime dateBookAvailableNotification;

    private LocalDateTime dateEndReservation;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn (name = "book_id")
    private Book book;



    public Reservation() {
    }

    public Reservation(BookReference bookReference, Librairy librairy, Customer customer, LocalDateTime dateReservation, LocalDateTime dateBookAvailableNotification, LocalDateTime dateEndReservation) {
        this.bookReference = bookReference;
        this.librairy = librairy;
        this.customer = customer;
        this.dateReservation = dateReservation;
        this.dateBookAvailableNotification = dateBookAvailableNotification;
        this.dateEndReservation = dateEndReservation;
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

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDateTime getDateBookAvailableNotification() {
        return dateBookAvailableNotification;
    }

    public void setDateBookAvailableNotification(LocalDateTime dateBookAvailableNotification) {
        this.dateBookAvailableNotification = dateBookAvailableNotification;
    }

    public LocalDateTime getDateEndReservation() {
        return dateEndReservation;
    }

    public void setDateEndReservation(LocalDateTime dateEndReservation) {
        this.dateEndReservation = dateEndReservation;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
