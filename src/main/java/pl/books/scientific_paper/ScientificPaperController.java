package pl.books.scientific_paper;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/papers")
public class ScientificPaperController {

    private final ScientificPaperService scientificPaperService;

    public ScientificPaperController(ScientificPaperService scientificPaperService) {
        this.scientificPaperService = scientificPaperService;
    }

    @GetMapping("/{id}")
    ScientificPaperDTO getScientificPaperById(@PathVariable Long id) {
        return scientificPaperService.getScientificPaperById(id);
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

    @GetMapping("/by-params")
    Page<ScientificPaperDTO> getAllPapersByParams(@RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer size,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String desc,
                                                  @RequestParam(required = false) Set<Long> authorsId,
                                                  @RequestParam(required = false) String field,
                                                  @RequestParam(required = false) String university,
                                                  @RequestParam(required = false) Boolean isForAdults) {
        return scientificPaperService.getAllByExample(page, size, name, desc, authorsId, field, university, isForAdults);
    }

    @PostMapping
    ScientificPaperDTO addNewPaper(@RequestBody @Validated(value = AddScientificPaper.class) ScientificPaperDTO dto) {
        return scientificPaperService.addNewScientificPaper(dto);
    }

    @DeleteMapping("/{id}")
    ScientificPaperDTO deleteScientificPaper(@PathVariable Long id) {
        return scientificPaperService.removeScientificPaper(id);
    }

    @PutMapping("/{id}")
    ScientificPaperDTO updateScientificPaper(@PathVariable @Validated(value = UpdateScientificPaper.class) Long id, @RequestBody ScientificPaperDTO dto) {
        return scientificPaperService.updateScientificPaper(id, dto);
    }
}
