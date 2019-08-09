package com.berthoud.p7.webserviceapp.model.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable=false)
    private String name;

    @ManyToMany (mappedBy = "tags")
    private Set<BookReference> bookReferences;

    public Tag(String name, Set<BookReference> bookReferences) {
        this.name = name;
        this.bookReferences = bookReferences;
    }

    public Tag(){

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

    public Set<BookReference> getBookReferences() {
        return bookReferences;
    }

    public void setBookReferences(Set<BookReference> bookReferences) {
        this.bookReferences = bookReferences;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}


