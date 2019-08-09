package p7.webapp.model.beans;

public class Availability {

    private String librairyName;
    private int BookReferenceId;
    private int amountBooks;
    private int amountAvailableBooks;

    public Availability(String librairyName, int bookReferenceId, int amountBooks, int amountAvailableBooks) {
        this.librairyName = librairyName;
        BookReferenceId = bookReferenceId;
        this.amountBooks = amountBooks;
        this.amountAvailableBooks = amountAvailableBooks;
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
}



