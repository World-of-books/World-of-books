package pl.books.audiobook;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.books.author.Author;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AudiobookDTO {

    private Long id;
    private String title;
    private String description;
//    private List<Author> authors;
    private List<AudiobookAuthorDTO> authors;
    private Boolean isForAdults;
    private Integer length; // seconds
    private LocalDate publishedDate;
    private String isbn;
    private String publishingHouse;
    private int quantity;
}
