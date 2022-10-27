package pl.books.author;

import pl.books.scientific_paper.ScientificPaperEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany
    private List<ScientificPaperEntity> publications;

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(String firstName, String lastName, List<ScientificPaperEntity> publications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.publications = publications;
    }

    public static Author of(String firstName, String lastName) {
        return new Author(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", publications=" + publications +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(publications, author.publications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, publications);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ScientificPaperEntity> getPublications() {
        return publications;
    }

    public void setPublications(List<ScientificPaperEntity> publications) {
        this.publications = publications;
    }
}
