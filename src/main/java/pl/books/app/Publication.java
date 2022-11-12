package pl.books.app;

import pl.books.author.AuthorEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "publications", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<AuthorEntity> authors = new HashSet<>();

    public Publication() {
    }

    public Publication(Long id, String name, Set<AuthorEntity> authors) {
        this.id = id;
        this.name = name;
        this.authors = authors;
    }

    public Publication(String name, Set<AuthorEntity> authors) {
        this.name = name;
        this.authors = authors;
    }

    public Publication(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        List<String> authorsData = authors.stream().map(author -> author.getId() + " " + author.getFirstName() + " " + author.getLastName()).toList();
        return "Publication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors=" + authorsData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publication)) return false;
        Publication that = (Publication) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && (authors == null || that.authors == null || authors.containsAll(that.authors));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }
}
