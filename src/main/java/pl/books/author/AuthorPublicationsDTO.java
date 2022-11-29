package pl.books.author;

import lombok.Getter;
import lombok.Setter;
import pl.books.audiobook.AudiobookDTO;
import pl.books.book.BookDTO;
import pl.books.scientific_paper.ScientificPaperDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AuthorPublicationsDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Set<ScientificPaperDTO> publications = new HashSet<>();
    private List<AudiobookDTO> audiobooks = new ArrayList<>();
    private List<BookDTO> books = new ArrayList<>();

}
