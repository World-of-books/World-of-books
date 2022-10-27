package pl.books.book;


import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    Page<BookDTO> getAllBooks(@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer size) {
        return bookService.getAllBooks(page, size);
    }

    @GetMapping("/{title}")
    Page<BookDTO> getBookByTitle(@PathVariable String title,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false)Integer size){
        return bookService.findAllByTitle(page, size, title);
    }

    @PostMapping
    BookDTO addBook(@RequestBody BookDTO bookDTO){
        return bookService.addBook(bookDTO);
    }

    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable Integer id){
        bookService.deleteBook(id);
    }

    @PutMapping("/{update}")
    void updateBook(@RequestBody BookDTO bookDTO){
        bookService.updateBook(bookDTO);
    }

}





