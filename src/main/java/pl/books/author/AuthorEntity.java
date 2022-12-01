package pl.books.author;

import pl.books.audiobook.AudiobookEntity;
import pl.books.book.BookEntity;
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
    @ManyToMany(mappedBy = "authors",  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ScientificPaperEntity> publications = new HashSet<>();
    @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AudiobookEntity> audiobooks = new ArrayList<>();

    @ManyToMany
    private List<BookEntity> books = new ArrayList<>();

    public AuthorEntity() {
    }

    public List<AudiobookEntity> getAudiobooks() {
        return audiobooks;
    }

    public void setAudiobooks(List<AudiobookEntity> audiobooks) {
        this.audiobooks = audiobooks;
    }

    public AuthorEntity(Long id) {
        this.id = id;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    public AuthorEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorEntity(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorEntity(Long id, String firstName, String lastName, Set<ScientificPaperEntity> publications) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.publications = publications;
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
        List<String> publicationsData = publications.stream().map(pub -> pub.getId() + " " + pub.getName()).toList();
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", publications='" + publicationsData + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorEntity)) return false;
        AuthorEntity that = (AuthorEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && (publications == null || that.publications == null || publications.containsAll(that.publications));
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

    public List<BookEntity> getBooks() {
        return books;
    }
}
