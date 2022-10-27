package pl.books.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorDTO {

    private Long id;
    private String firstName;
    private String lastName;



}
