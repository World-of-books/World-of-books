package pl.books.audiobook;

import lombok.*;
import pl.books.app.Publication;
import pl.books.author.AuthorEntity;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class AudiobookEntity extends Publication {

    private String description;
    private Boolean isForAdults;
    private Integer length; // seconds
    private LocalDate publishedDate;
    private String isbn;
    private String publishingHouse;
    private int quantity;

    public AudiobookEntity(String name, Set<AuthorEntity> authors, String description, Boolean isForAdults, Integer length, LocalDate publishedDate, String isbn, String publishingHouse) {
        super(name, authors);
        this.description = description;
        this.isForAdults = isForAdults;
        this.length = length;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
        this.publishingHouse = publishingHouse;
    }

    public AudiobookEntity(Long id, String name, Set<AuthorEntity> authors, String description, Boolean isForAdults, Integer length, LocalDate publishedDate, String isbn, String publishingHouse) {
        super(id, name, authors);
        this.description = description;
        this.isForAdults = isForAdults;
        this.length = length;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
        this.publishingHouse = publishingHouse;
    }
}
