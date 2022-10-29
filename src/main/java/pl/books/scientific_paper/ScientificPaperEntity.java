package pl.books.scientific_paper;

import pl.books.author.AuthorEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity(name = "paper")
public class ScientificPaperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "publications", cascade = CascadeType.PERSIST)
    private Set<AuthorEntity> authors = new HashSet<>();
    private FieldOfStudy field;
    private String university;
    private Boolean isForAdults;
    private Integer pages;
    private LocalDate publishedDate;

    public ScientificPaperEntity() {
    }

    public ScientificPaperEntity(String name, String description, Set<AuthorEntity> authors, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate) {
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.field = field;
        this.university = university;
        this.isForAdults = isForAdults;
        this.pages = pages;
        this.publishedDate = publishedDate;
    }

    public static ScientificPaperEntity of(String name, String description, Set<AuthorEntity> authors, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate) {
        return new ScientificPaperEntity(name, description, authors, field, university, isForAdults, pages, publishedDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScientificPaperEntity)) return false;
        ScientificPaperEntity that = (ScientificPaperEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(authors, that.authors) && field == that.field && Objects.equals(university, that.university) && Objects.equals(isForAdults, that.isForAdults) && Objects.equals(pages, that.pages) && Objects.equals(publishedDate, that.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, field, university, isForAdults, pages, publishedDate);
    }

    @Override
    public String toString() {
        return "ScientificPaperEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", field=" + field +
                ", university='" + university + '\'' +
                ", isForAdults=" + isForAdults +
                ", pages=" + pages +
                ", publishedDate=" + publishedDate +
                '}';
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Optional<Set<AuthorEntity>> getAuthors() {
        return Optional.ofNullable(authors);
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }

    public Optional<FieldOfStudy> getField() {
        return Optional.ofNullable(field);
    }

    public void setField(FieldOfStudy field) {
        this.field = field;
    }

    public Optional<String> getUniversity() {
        return Optional.ofNullable(university);
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Optional<Boolean> getForAdults() {
        return Optional.ofNullable(isForAdults);
    }

    public void setForAdults(Boolean forAdults) {
        isForAdults = forAdults;
    }

    public Optional<Integer> getPages() {
        return Optional.ofNullable(pages);
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Optional<LocalDate> getPublishedDate() {
        return Optional.ofNullable(publishedDate);
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}
