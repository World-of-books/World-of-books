package pl.books.scientific_paper;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/papers")
public class ScientificPaperController {

    private final ScientificPaperService scientificPaperService;

    public ScientificPaperController(ScientificPaperService scientificPaperService) {
        this.scientificPaperService = scientificPaperService;
    }

    @GetMapping
    Page<ScientificPaperDTO> getAllPapers(@RequestParam(required = false) Integer page,
                                          @RequestParam(required = false) Integer size) {
        return scientificPaperService.getAllScientificPapers(page, size);
    }

    @PostMapping("/by-authors")
    Page<ScientificPaperDTO> getAllPapersByAuthors(@RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size,
                                                   @RequestBody List<Long> authorsId) {
        return scientificPaperService.getAllScientificPapersByAuthorId(page, size, authorsId);
    }
}
