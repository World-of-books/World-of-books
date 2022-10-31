package pl.books.scientific_paper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ScientificPaperDTO {
    private Long id;
    @NotNull(groups = AddScientificPaper.class, message = "Scientific paper name cannot be empty")
    @Size(min = 3, max = 500, groups = {AddScientificPaper.class, UpdateScientificPaper.class}, message = "Scientific paper should be at least 3 characters long")
    private String name;
    @NotNull(groups = AddScientificPaper.class, message = "Scientific paper description cannot be empty")
    private String description;
    @NotNull(groups = AddScientificPaper.class, message = "Scientific paper most certainly has any author")
    private List<ScientificPaperAuthorDTO> authors;
    @NotNull(groups = AddScientificPaper.class, message = "Scientific paper most certainly has any field of study")
    private String field;
    @NotNull(groups = AddScientificPaper.class, message = "Scientific paper university cannot be empty")
    private String university;
    @NotNull(groups = AddScientificPaper.class, message = "Scientific paper is either for adults only or not")
    private Boolean isForAdults;
    @NotNull(groups = AddScientificPaper.class, message = "Scientific paper most certainly has any pages")
    @Positive(groups = {AddScientificPaper.class, UpdateScientificPaper.class}, message = "Pages cannot be negative")
    private Integer pages;
    @NotNull(groups = AddScientificPaper.class, message = "Scientific paper published date cannot be empty")
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

interface AddScientificPaper {
}

interface UpdateScientificPaper {
}