package pl.books.scientific_paper;

import pl.books.author.AuthorEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "paper")
public class ScientificPaperEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "publications", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<AuthorEntity> authors = new HashSet<>();
    private FieldOfStudy field;
    private String university;
    private Boolean isForAdults;
    private Integer pages;
    private LocalDate publishedDate;
    private Integer quantity;

    public ScientificPaperEntity() {
    }

    public ScientificPaperEntity(Long id) {
        this.id = id;
    }

    public ScientificPaperEntity(String name, String description, Set<AuthorEntity> authors, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate, Integer quantity) {
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.field = field;
        this.university = university;
        this.isForAdults = isForAdults;
        this.pages = pages;
        this.publishedDate = publishedDate;
        this.quantity = quantity;
    }

    public ScientificPaperEntity(Long id, String name, String description, Set<AuthorEntity> authors, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.field = field;
        this.university = university;
        this.isForAdults = isForAdults;
        this.pages = pages;
        this.publishedDate = publishedDate;
        this.quantity = quantity;
    }

    public static ScientificPaperEntity of(String name, String description, Set<AuthorEntity> authors, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate, Integer quantity) {
        return new ScientificPaperEntity(name, description, authors, field, university, isForAdults, pages, publishedDate, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScientificPaperEntity)) return false;
        ScientificPaperEntity that = (ScientificPaperEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && (authors == null || that.authors == null || authors.containsAll(that.authors)) && field == that.field && Objects.equals(university, that.university) && Objects.equals(isForAdults, that.isForAdults) && Objects.equals(pages, that.pages) && Objects.equals(publishedDate, that.publishedDate) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, field, university, isForAdults, pages, publishedDate, quantity);
    }

    @Override
    public String toString() {
        List<String> authorsData = authors.stream().map(author -> author.getId() + " " + author.getFirstName() + " " + author.getLastName()).toList();
        return "ScientificPaperEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authorsData +
                ", field=" + field +
                ", university='" + university + '\'' +
                ", isForAdults=" + isForAdults +
                ", pages=" + pages +
                ", publishedDate=" + publishedDate +
                ", quantity=" + quantity +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }

    public FieldOfStudy getField() {
        return field;
    }

    public void setField(FieldOfStudy field) {
        this.field = field;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Boolean getForAdults() {
        return isForAdults;
    }

    public void setForAdults(Boolean forAdults) {
        isForAdults = forAdults;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
