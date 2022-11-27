package pl.books.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.books.audiobook.AudiobookDTO;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping("/addAuthor")
    public AuthorEntity addAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.createAuthor(authorDTO);
    }

    @GetMapping("/author/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/authors")
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAuthors();
    }

    @PutMapping("/updateAuthor/{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        return authorService.updateAuthor(id, authorDTO);
    }

    @DeleteMapping("/author/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthorById(id);
    }
}
