package pl.books.scientific_paper;

import io.swagger.models.auth.In;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ScientificPaperDTO {
    private Long id;
    private String name;
    private String description;
    private List<ScientificPaperAuthorDTO> authors;
    private String field;
    private String university;
    private Boolean isForAdults;
    private Integer pages;
    private String publishedDate;

    public ScientificPaperDTO(Long id, String name, String description, List<ScientificPaperAuthorDTO> authors, String field, String university, Boolean isForAdults, Integer pages, String publishedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.field = field;
        this.university = university;
        this.isForAdults = isForAdults;
        this.pages = pages;
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "ScientificPaperDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                ", field='" + field + '\'' +
                ", university='" + university + '\'' +
                ", isForAdults=" + isForAdults +
                ", pages=" + pages +
                ", publishedDate='" + publishedDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScientificPaperDTO)) return false;
        ScientificPaperDTO that = (ScientificPaperDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(authors == null ? 0 : authors.size(), that.authors == null ? 0 : that.authors.size()) && Objects.equals(field, that.field) && Objects.equals(university, that.university) && Objects.equals(isForAdults, that.isForAdults) && Objects.equals(pages, that.pages) && Objects.equals(publishedDate, that.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, authors == null ? 0 : authors.size(), field, university, isForAdults, pages, publishedDate);
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

    public Optional<List<ScientificPaperAuthorDTO>> getAuthors() {
        return Optional.ofNullable(authors);
    }

    public void setAuthors(List<ScientificPaperAuthorDTO> authors) {
        this.authors = authors;
    }

    public Optional<String> getField() {
        return Optional.ofNullable(field);
    }

    public void setField(String field) {
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

    public Optional<String> getPublishedDate() {
        return Optional.ofNullable(publishedDate);
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
}
