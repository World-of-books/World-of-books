package pl.books.author;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/addAuthor")
    public AuthorEntity addAuthor(@RequestBody AuthorPublicationsDTO authorPublicationsDTO) {
        return authorService.createAuthor(authorPublicationsDTO);
    }

    @GetMapping("/author/{id}")
    public AuthorPublicationsDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/authors")
    public List<AuthorPublicationsDTO> getAllAuthors() {
        return authorService.getAuthors();
    }

    @PutMapping("/updateAuthor/{id}")
    public AuthorPublicationsDTO updateAuthor(@PathVariable Long id, @RequestBody AuthorPublicationsDTO authorPublicationsDTO) {
        return authorService.updateAuthor(id, authorPublicationsDTO);
    }

    @DeleteMapping("/author/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthorById(id);
    }
}
