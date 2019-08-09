package com.berthoud.p7.webserviceapp.model.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class BookReference extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String authorFirstName;

    @Column(nullable=false)
    private String authorSurname;

    @Column(nullable=false)
    private String isbn13;

    @Column(nullable=false)
    private String publisher;

    @Column(nullable=false)
    private String summary;

    @Column(nullable=false)
    private String yearPublication;

    @OneToMany (mappedBy = "bookReference", cascade = CascadeType.ALL)
    private Set<Book> books;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable (name = "book_references_tags",
            joinColumns = @JoinColumn (name = "bookReference_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags;

    public BookReference(String title, String authorFirstName, String authorSurname, String isbn13, String publisher, String summary, String yearPublication, Set<Book> books, Set<Tag> tags) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorSurname = authorSurname;
        this.isbn13 = isbn13;
        this.publisher = publisher;
        this.summary = summary;
        this.yearPublication = yearPublication;
        this.books = books;
        this.tags = tags;
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
}
