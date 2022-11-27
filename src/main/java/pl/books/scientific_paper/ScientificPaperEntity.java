package pl.books.scientific_paper;

import pl.books.app.Publication;
import pl.books.author.AuthorEntity;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity(name = "paper")
public class ScientificPaperEntity extends Publication {

    private String description;
    private FieldOfStudy field;
    private String university;
    private Boolean isForAdults;
    private Integer pages;
    private LocalDate publishedDate;
    private Integer quantity;

    public ScientificPaperEntity() {
    }

    public ScientificPaperEntity(Long id, String name, Set<AuthorEntity> authors, String description, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate, Integer quantity) {
        super(id, name, authors);
        this.description = description;
        this.field = field;
        this.university = university;
        this.isForAdults = isForAdults;
        this.pages = pages;
        this.publishedDate = publishedDate;
        this.quantity = quantity;
    }

    public ScientificPaperEntity(String name, Set<AuthorEntity> authors, String description, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate, Integer quantity) {
        super(name, authors);
        this.description = description;
        this.field = field;
        this.university = university;
        this.isForAdults = isForAdults;
        this.pages = pages;
        this.publishedDate = publishedDate;
        this.quantity = quantity;
    }

    public ScientificPaperEntity(String name, Set<AuthorEntity> authors, String description, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate) {
        super(name, authors);
        this.description = description;
        this.field = field;
        this.university = university;
        this.isForAdults = isForAdults;
        this.pages = pages;
        this.publishedDate = publishedDate;
    }

    public static ScientificPaperEntity of(String name, Set<AuthorEntity> authors, String description, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate, Integer quantity) {
        return new ScientificPaperEntity(name,authors, description, field, university, isForAdults, pages, publishedDate, quantity);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScientificPaperEntity)) return false;
        if (!super.equals(o)) return false;
        ScientificPaperEntity that = (ScientificPaperEntity) o;
        return Objects.equals(description, that.description) && field == that.field && Objects.equals(university, that.university) && Objects.equals(isForAdults, that.isForAdults) && Objects.equals(pages, that.pages) && Objects.equals(publishedDate, that.publishedDate) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, field, university, isForAdults, pages, publishedDate, quantity);
    }

    @Override
    public String toString() {
        return "ScientificPaperEntity{" +
                "description='" + description + '\'' +
                ", field=" + field +
                ", university='" + university + '\'' +
                ", isForAdults=" + isForAdults +
                ", pages=" + pages +
                ", publishedDate=" + publishedDate +
                ", quantity=" + quantity +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
