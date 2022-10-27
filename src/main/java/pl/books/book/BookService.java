package pl.books.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookService {



    private final BookRepository bookRepository;

    private final BookTransformer bookTransformer;


    public BookService(BookRepository bookRepository, BookTransformer bookTransformer) {
        this.bookRepository = bookRepository;
        this.bookTransformer = bookTransformer;
    }

    Page<BookDTO> getAllBooks(Integer page, Integer size) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 50 : size;

        Page<BookEntity> entities = bookRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "title")));
        return page == null ? Page.empty() : entities.map(bookTransformer::toDTO);
    }

    Page<BookDTO> findAllByTitle(Integer page, Integer size, String title){
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 50 : size;

        Page<BookEntity> title1 = bookRepository.findAllByTitleContainsIgnoreCase(title, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "title")));
        return page == null ? Page.empty() : title1.map(bookTransformer::toDTO);
    }

    BookDTO addBook(BookDTO bookDTO){
        bookRepository.findByIsbn(bookDTO.getIsbn()).ifPresent(book ->{
            throw new IllegalArgumentException("Book already exist with isbn number: " + book.getIsbn());
        });
        BookEntity bookEntity = bookTransformer.toEntity(bookDTO);
        BookEntity bookent = bookRepository.save(bookEntity);
        return bookTransformer.toDTO(bookent);

    }

      void deleteBook(Integer id){
          BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() -> {
              throw new NoSuchElementException("Book " + id + " not exist");
          });
          bookRepository.delete(bookEntity);
    }

    void updateBook(BookDTO bookDTO) {
        BookEntity bookEntity = bookTransformer.toEntity(bookDTO);
        bookRepository.save(bookEntity);

    }



}

