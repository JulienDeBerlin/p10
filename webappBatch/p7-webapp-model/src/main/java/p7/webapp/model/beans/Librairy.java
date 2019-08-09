package p7.webapp.model.beans;

import java.util.Set;


public class Librairy {


    private int id;
    private String name;


    private Address address;

    private Set<Book> books;

    public Librairy(String name, Address address, Set<Book> books) {
        this.name = name;
        this.address = address;
        this.books = books;
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
}