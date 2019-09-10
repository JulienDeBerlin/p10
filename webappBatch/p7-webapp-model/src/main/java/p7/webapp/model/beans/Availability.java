package p7.webapp.model.beans;

import java.time.LocalDate;

public class Availability {

    private String librairyName;
    private int librairyId;
    private int BookReferenceId;
    private int amountBooks;
    private int amountAvailableBooks;
    private int amountReservations;
    private LocalDate closedDateEnd;

    public Availability(String librairyName, int librairyId, int bookReferenceId, int amountBooks, int amountAvailableBooks, int amountReservations, LocalDate closedDateEnd) {
        this.librairyName = librairyName;
        this.librairyId = librairyId;
        BookReferenceId = bookReferenceId;
        this.amountBooks = amountBooks;
        this.amountAvailableBooks = amountAvailableBooks;
        this.amountReservations = amountReservations;
        this.closedDateEnd = closedDateEnd;
    }

    public Availability() {
    }

    public String getLibrairyName() {
        return librairyName;
    }

    public void setLibrairyName(String librairyName) {
        this.librairyName = librairyName;
    }

    public int getBookReferenceId() {
        return BookReferenceId;
    }

    public void setBookReferenceId(int bookReferenceId) {
        BookReferenceId = bookReferenceId;
    }

    public int getAmountBooks() {
        return amountBooks;
    }

    public void setAmountBooks(int amountBooks) {
        this.amountBooks = amountBooks;
    }

    public int getAmountAvailableBooks() {
        return amountAvailableBooks;
    }

    public void setAmountAvailableBooks(int amountAvailableBooks) {
        this.amountAvailableBooks = amountAvailableBooks;
    }

    public int getLibrairyId() {
        return librairyId;
    }

    public void setLibrairyId(int librairyId) {
        this.librairyId = librairyId;
    }

    public int getAmountReservations() {
        return amountReservations;
    }

    public void setAmountReservations(int amountReservations) {
        this.amountReservations = amountReservations;
    }

    public LocalDate getClosedDateEnd() {
        return closedDateEnd;
    }

    public void setClosedDateEnd(LocalDate closedDateEnd) {
        this.closedDateEnd = closedDateEnd;
    }
}



