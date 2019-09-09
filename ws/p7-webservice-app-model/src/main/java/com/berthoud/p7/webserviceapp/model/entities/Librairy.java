package com.berthoud.p7.webserviceapp.model.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Librairy extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "address_id")
    private Address address;

    @OneToMany (mappedBy = "librairy", cascade = CascadeType.ALL)
    private Set<Book> books;

    @OrderBy("positionQueue ASC ")
    @OneToMany (mappedBy = "bookReference", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

    public Librairy(String name, Address address, Set<Book> books, Set<Reservation> reservations) {
        this.name = name;
        this.address = address;
        this.books = books;
        this.reservations = reservations;
    }

    public Librairy(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}