package pl.books.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.books.audiobook.AudiobookDTO;
import pl.books.audiobook.AudiobookService;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

//    @GetMapping("/audiobook/{id}")
//    public AudiobookDTO getAudiobookById(@PathVariable Long id) {
//        return authorService.getAudiobookById(id);
//    }

    @GetMapping("/authors")
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAuthors();
    }

}
