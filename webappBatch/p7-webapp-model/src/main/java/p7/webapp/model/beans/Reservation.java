package p7.webapp.model.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {


    private int id;

    private BookReference bookReference;

    private Librairy librairy;

    private Customer customer;

    private LocalDateTime dateReservation;

    private LocalDateTime dateBookAvailableNotification;

    private LocalDateTime dateEndReservation;

    private int positionInReservationList;

    private LocalDate plannedNextReturn;


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

    public int getPositionInReservationList() {
        return positionInReservationList;
    }

    public void setPositionInReservationList(int positionInReservationList) {
        this.positionInReservationList = positionInReservationList;
    }

    public LocalDate getPlannedNextReturn() {
        return plannedNextReturn;
    }

    public void setPlannedNextReturn(LocalDate plannedNextReturn) {
        this.plannedNextReturn = plannedNextReturn;
    }

    public LocalDateTime getDateEndReservation() {
        return dateEndReservation;
    }

    public void setDateEndReservation(LocalDateTime dateEndReservation) {
        this.dateEndReservation = dateEndReservation;
    }
}
