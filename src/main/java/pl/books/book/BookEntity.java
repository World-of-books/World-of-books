package pl.books.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import pl.books.author.AuthorEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    @ManyToMany(mappedBy = "books")
    private List<AuthorEntity> author = new ArrayList<>();

    @Column(unique = true)
    private String isbn;

    private LocalDate releaseDate;

    private int numberOfPages;

    private String publishingHouse;

    private int quantity;


}
