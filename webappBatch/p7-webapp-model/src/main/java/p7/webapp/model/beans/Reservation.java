package p7.webapp.model.beans;

import java.time.LocalDate;

public class Reservation {


    private int id;

    private BookReference bookReference;

    private Librairy librairy;

    private Customer customer;

    private LocalDate dateReservation;

    private LocalDate dateBookAvailableNotification;

    private int positionInreservationList;

    private LocalDate plannedNextreturn;


    public Reservation() {
    }

    public Reservation(int id, BookReference bookReference, Librairy librairy, Customer customer, LocalDate dateReservation, LocalDate dateBookAvailableNotification) {
        this.id = id;
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

    public int getPositionInreservationList() {
        return positionInreservationList;
    }

    public void setPositionInreservationList(int positionInreservationList) {
        this.positionInreservationList = positionInreservationList;
    }

    public LocalDate getPlannedNextreturn() {
        return plannedNextreturn;
    }

    public void setPlannedNextreturn(LocalDate plannedNextreturn) {
        this.plannedNextreturn = plannedNextreturn;
    }
}
