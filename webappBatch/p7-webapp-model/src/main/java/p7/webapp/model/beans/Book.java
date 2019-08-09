package p7.webapp.model.beans;

import java.time.LocalDate;
import java.util.Set;



public class Book {


    private int id;

    private LocalDate datePurchase;

    public enum Status {
        AVAILABLE, BOOKED, BORROWED
    }

    private Status status;


    private Librairy librairy;

    private Set<Loan> loans;


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




