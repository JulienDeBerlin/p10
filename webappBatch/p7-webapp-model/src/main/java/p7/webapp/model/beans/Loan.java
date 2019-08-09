package p7.webapp.model.beans;

import java.time.LocalDate;

public class Loan implements Comparable<Loan>{


    private int id;

    private LocalDate dateBegin;

    private LocalDate dateEnd;

    private int numberExtensions;

    private LocalDate dateBack;

    private Customer customer;

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

    @Override
    public int compareTo(Loan o) {
        return this.getDateEnd().isBefore(o.dateEnd)?-1:getDateEnd().isAfter(o.dateEnd)?1:0;
    }
}
