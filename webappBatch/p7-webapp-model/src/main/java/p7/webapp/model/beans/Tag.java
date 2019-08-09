package p7.webapp.model.beans;

import java.util.Objects;
import java.util.Set;

public class Tag {


    private int id;

    private String name;

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


