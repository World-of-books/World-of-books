package pl.books.scientific_paper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;

import java.util.List;

@Service
public class ScientificPaperService {

    private final ScientificPaperRepository scientificPaperRepository;
    private final AuthorRepository authorRepository;
    private final ScientificPaperTransformer scientificPaperTransformer;

    public ScientificPaperService(ScientificPaperRepository scientificPaperRepository, AuthorRepository authorRepository, ScientificPaperTransformer scientificPaperTransformer) {
        this.scientificPaperRepository = scientificPaperRepository;
        this.authorRepository = authorRepository;
        this.scientificPaperTransformer = scientificPaperTransformer;
    }

    Page<ScientificPaperDTO> getAllScientificPapers(Integer page, Integer size) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 50 : size;

        Page<ScientificPaperEntity> entities = scientificPaperRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        return entities == null ? Page.empty() : entities.map(scientificPaperTransformer::toDTO);
    }


    Page<ScientificPaperDTO> getAllScientificPapersByAuthorId(Integer page, Integer size,  List<Long> authorsId) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 50 : size;
        List<AuthorEntity> authors = authorRepository.findAllById(authorsId);
        Page<ScientificPaperEntity> allByAuthorsIn = scientificPaperRepository.findAllByAuthorsIn(authors, PageRequest.of(page, size));
        return allByAuthorsIn == null ? Page.empty() : allByAuthorsIn.map(scientificPaperTransformer::toDTO);
    }
}
