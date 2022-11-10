package pl.books.audiobook;

import lombok.*;
import pl.books.author.Author;
import pl.books.scientific_paper.FieldOfStudy;
import pl.books.scientific_paper.ScientificPaperEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class AudiobookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @ManyToMany(mappedBy = "audiobooks")
    private List<Author> authors = new ArrayList<>();
    private Boolean isForAdults;
    private Integer length; // seconds
    private LocalDate publishedDate;
    private String isbn;
    private String publishingHouse;
    private int quantity;

    public AudiobookEntity(String title, String description, List<Author> authors, Boolean isForAdults, Integer length, LocalDate publishedDate, String isbn, String publishingHouse, int quantity) {
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.isForAdults = isForAdults;
        this.length = length;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
        this.publishingHouse = publishingHouse;
        this.quantity = quantity;
    }

    static AudiobookEntity of(String title, String description, List<Author> authors, Boolean isForAdults, Integer length, LocalDate publishedDate, String isbn, String publishingHouse, int quantity) {
        return new AudiobookEntity(title, description, authors, isForAdults, length, publishedDate, isbn, publishingHouse, quantity);
    }


}
