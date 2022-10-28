package pl.books.scientific_paper;

import java.time.LocalDate;
import java.util.List;

public class ScientificPaperDTO {
    private Long id;
    private String name;
    private String description;
    private List<ScientificPaperAuthorDTO> authors;
    private FieldOfStudy field;
    private String university;
    private Boolean isForAdults;
    private Integer pages;
    private LocalDate publishedDate;

    public ScientificPaperDTO(Long id, String name, String description, List<ScientificPaperAuthorDTO> authors, FieldOfStudy field, String university, Boolean isForAdults, Integer pages, LocalDate publishedDate) {
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

    public List<ScientificPaperAuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<ScientificPaperAuthorDTO> authors) {
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
}
