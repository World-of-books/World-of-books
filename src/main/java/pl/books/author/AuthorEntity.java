package pl.books.author;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import pl.books.scientific_paper.ScientificPaperEntity;

import javax.persistence.*;
import java.util.*;

@Entity(name = "author")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany
    @JoinTable(name="author_paper")
    private Set<ScientificPaperEntity> publications = new HashSet<>();

    public AuthorEntity() {
    }

    public AuthorEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorEntity(String firstName, String lastName, Set<ScientificPaperEntity> publications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.publications = publications;
    }

    public static AuthorEntity of(String firstName, String lastName) {
        return new AuthorEntity(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorEntity)) return false;
        AuthorEntity author = (AuthorEntity) o;
        return Objects.equals(id, author.id) && Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(publications, author.publications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
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

    public Set<ScientificPaperEntity> getPublications() {
        return publications;
    }

    public void setPublications(Set<ScientificPaperEntity> publications) {
        this.publications = publications;
    }
}
