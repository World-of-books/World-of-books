package pl.books.scientific_paper;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
