package pl.books.audiobook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import pl.books.author.AuthorRepository;


import java.util.List;

@RestController
public class AudiobookController {

    private final AudiobookService audiobookService;

    @Autowired
    public AudiobookController(@Qualifier("audio_service") AudiobookService audiobookService) {
        this.audiobookService = audiobookService;
    }

    @PostMapping("/addAudiobook")
    public AudiobookEntity addAudiobook(@RequestBody AudiobookDTO audiobook) {
        return audiobookService.createAudiobook(audiobook);
    }

    @GetMapping("/audiobook/{id}")
    public AudiobookDTO getAudiobookById(@PathVariable Long id) {
        return audiobookService.getAudiobookById(id);
    }

    @GetMapping("/audiobooks")
    public List<AudiobookDTO> getAllAudiobooks() {
        return audiobookService.getAudiobooks();
    }

    @PutMapping("/updateAudiobook/{id}")
    public AudiobookDTO updateAudiobook(@PathVariable Long id, @RequestBody AudiobookDTO audiobookDTO) {
        return audiobookService.updateAudiobook(id, audiobookDTO);
    }

    @DeleteMapping("/audiobook/{id}")
    public String deleteAudiobook(@PathVariable Long id) {
        return audiobookService.deleteAudiobookById(id);
    }

}