package pl.books.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import pl.books.book.BookEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany
    private List<BookEntity> books = new ArrayList<>();


    public static Author of(String testOne, String test) {
        return new Author(testOne, test);
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}