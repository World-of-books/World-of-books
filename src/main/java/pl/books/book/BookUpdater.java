package pl.books.book;

import org.springframework.stereotype.Component;
import pl.books.author.AuthorRepository;

import java.util.Collections;
import java.util.List;

@Component
public class BookUpdater {
    
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookTransformer bookTransformer;

    public BookUpdater(BookRepository bookRepository, AuthorRepository authorRepository, BookTransformer bookTransformer) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookTransformer = bookTransformer;
    }
    
//    BookDTO update(BookDTO bookDTO,BookEntity entityToUpdate){
//        List<Long> authorsId = Collections.emptyList();
//        if (bookDTO.getAuthors();
//    }
}
