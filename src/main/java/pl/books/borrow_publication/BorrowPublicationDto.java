package pl.books.borrow_publication;

import pl.books.author.AuthorEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BorrowPublicationDto {
    Long id;
    Long publicationId;
    private String publicationName;
    private Set<AuthorEntity> publicationAuthors = new HashSet<>();
    private Long userId;
    private String username;
    private String userEmail;
    LocalDate borrowDate;
    LocalDate requiredReturnDate;
    LocalDate publicationReturnedDate;

    public BorrowPublicationDto(Long id, Long publicationId, String publicationName, Set<AuthorEntity> publicationAuthors, Long userId, String username, String userEmail, LocalDate borrowDate, LocalDate requiredReturnDate, LocalDate publicationReturnedDate) {
        this.id = id;
        this.publicationId = publicationId;
        this.publicationName = publicationName;
        this.publicationAuthors = publicationAuthors;
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.borrowDate = borrowDate;
        this.requiredReturnDate = requiredReturnDate;
        this.publicationReturnedDate = publicationReturnedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public Set<AuthorEntity> getPublicationAuthors() {
        return publicationAuthors;
    }

    public void setPublicationAuthors(Set<AuthorEntity> publicationAuthors) {
        this.publicationAuthors = publicationAuthors;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getRequiredReturnDate() {
        return requiredReturnDate;
    }

    public void setRequiredReturnDate(LocalDate requiredReturnDate) {
        this.requiredReturnDate = requiredReturnDate;
    }

    public LocalDate getPublicationReturnedDate() {
        return publicationReturnedDate;
    }

    public void setPublicationReturnedDate(LocalDate publicationReturnedDate) {
        this.publicationReturnedDate = publicationReturnedDate;
    }
}
