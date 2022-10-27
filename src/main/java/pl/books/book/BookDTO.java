package pl.books.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {


    private Integer id;

    private String title;

    private List<BookAuthorDTO> authors = new ArrayList<>();

    private String isbn;

    private LocalDate releaseDate;

    private int numberOfPages;

    private String publishingHouse;

    private int quantity;

//    public List<BookAuthorDTO> getAuthors(){
//        return authors;
//    }


}
