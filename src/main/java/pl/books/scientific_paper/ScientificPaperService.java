package pl.books.scientific_paper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ScientificPaperService {

    private final ScientificPaperRepository scientificPaperRepository;
    private final ScientificPaperTransformer scientificPaperTransformer;

    public ScientificPaperService(ScientificPaperRepository scientificPaperRepository, ScientificPaperTransformer scientificPaperTransformer) {
        this.scientificPaperRepository = scientificPaperRepository;
        this.scientificPaperTransformer = scientificPaperTransformer;
    }

    Page<ScientificPaperDTO> getAllScientificPapers(Integer page, Integer size) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 50 : size;

        Page<ScientificPaperEntity> entities = scientificPaperRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        return entities == null ? Page.empty() : entities.map(scientificPaperTransformer::toDTO);
    }
}
