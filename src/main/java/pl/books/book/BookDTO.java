package pl.books.book;

import pl.books.author.AuthorEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDTO {





    private Integer id;

    private String title;


    private List<AuthorEntity> author = new ArrayList<>();


    private String isbn;

    private LocalDate releaseDate;

    private int numberOfPages;

    private String publishingHouse;

    private int quantity;


}
