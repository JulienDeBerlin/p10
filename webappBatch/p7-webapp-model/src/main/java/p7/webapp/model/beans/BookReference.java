package p7.webapp.model.beans;

import java.util.List;
import java.util.Set;

public class BookReference {


    private int id;

    private String title;

    private String authorFirstName;

    private String authorSurname;

    private String isbn13;

    private String publisher;

    private String summary;

    private String yearPublication;

    private Set<Book> books;

    private Set<Tag> tags;

    private String tagsAsString;

    private int amountAvailableBooks;

    private List<Availability> availabilities;

    public BookReference(String title, String authorFirstName, String authorSurname, String isbn13, String publisher,
                         String summary, String yearPublication, Set<Book> books, Set<Tag> tags, int amountAvailableBooks) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorSurname = authorSurname;
        this.isbn13 = isbn13;
        this.publisher = publisher;
        this.summary = summary;
        this.yearPublication = yearPublication;
        this.books = books;
        this.tags = tags;
        this.amountAvailableBooks = amountAvailableBooks;
    }

    public BookReference(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getYearPublication() {
        return yearPublication;
    }

    public void setYearPublication(String yearPublication) {
        this.yearPublication = yearPublication;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public int getAmountAvailableBooks() {
        return amountAvailableBooks;
    }

    public void setAmountAvailableBooks(int amountAvailableBooks) {
        this.amountAvailableBooks = amountAvailableBooks;
    }

    public String getTagsAsString() {
        return tagsAsString;
    }

    public void setTagsAsString(String tagsAsString) {
        this.tagsAsString = tagsAsString;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }
}
