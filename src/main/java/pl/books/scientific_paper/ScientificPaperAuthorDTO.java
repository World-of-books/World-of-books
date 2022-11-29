package pl.books.scientific_paper;

import java.util.Objects;
import java.util.Optional;

public class ScientificPaperAuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public ScientificPaperAuthorDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ScientificPaperAuthorDTO() {
    }

    public ScientificPaperAuthorDTO(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ScientificPaperAuthorDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScientificPaperAuthorDTO)) return false;
        ScientificPaperAuthorDTO that = (ScientificPaperAuthorDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Optional<String> getFirstName() {
        return Optional.ofNullable(firstName);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Optional<String> getLastName() {
        return Optional.ofNullable(lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
