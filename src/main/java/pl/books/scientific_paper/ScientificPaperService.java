package pl.books.scientific_paper;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;

import java.util.*;

@Service
public class ScientificPaperService {

    private final ScientificPaperRepository scientificPaperRepository;
    private final AuthorRepository authorRepository;
    private final ScientificPaperTransformer scientificPaperTransformer;
    private final ScientificPaperUpdater scientificPaperUpdater;

    public ScientificPaperService(ScientificPaperRepository scientificPaperRepository, AuthorRepository authorRepository, ScientificPaperTransformer scientificPaperTransformer, ScientificPaperUpdater scientificPaperUpdater) {
        this.scientificPaperRepository = scientificPaperRepository;
        this.authorRepository = authorRepository;
        this.scientificPaperTransformer = scientificPaperTransformer;
        this.scientificPaperUpdater = scientificPaperUpdater;
    }

    Page<ScientificPaperDTO> getAllByExample(Integer page, Integer size, String name, String desc, List<Long> authorsId, FieldOfStudy field, String university, Boolean isForAdults) {
        Set<AuthorEntity> authors = null;
        if (authorsId != null) {
            authors = new HashSet<>(authorRepository.findAllById(authorsId));
        }
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<ScientificPaperEntity> example = Example.of(
                new ScientificPaperEntity(name, desc, authors, field, university, isForAdults, null, null), matcher);
        if (page != null && size != null && page == -1 && size == -1) {
            Pageable wholePage = Pageable.unpaged();
            return scientificPaperRepository.findAll(example, wholePage).map(scientificPaperTransformer::toDTO);
        }
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 50 : size;
        Page<ScientificPaperEntity> results = scientificPaperRepository.findAll(example, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        return results == null ? Page.empty() : results.map(scientificPaperTransformer::toDTO);
    }

    Page<ScientificPaperDTO> getAllScientificPapers(Integer page, Integer size) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 50 : size;

        Page<ScientificPaperEntity> entities = scientificPaperRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        return entities == null ? Page.empty() : entities.map(scientificPaperTransformer::toDTO);
    }


    Page<ScientificPaperDTO> getAllScientificPapersByAuthorId(Integer page, Integer size, List<Long> authorsId) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 50 : size;
        List<AuthorEntity> authors = authorRepository.findAllById(authorsId);
        Page<ScientificPaperEntity> allByAuthorsIn = scientificPaperRepository.findAllByAuthorsIn(authors, PageRequest.of(page, size));
        return allByAuthorsIn == null ? Page.empty() : allByAuthorsIn.map(scientificPaperTransformer::toDTO);
    }

    ScientificPaperDTO getScientificPaperById(Long id) {
        ScientificPaperEntity scientificPaperEntity = scientificPaperRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("No scientific paperwork found with id" + id);
        });
        return scientificPaperTransformer.toDTO(scientificPaperEntity);
    }

    ScientificPaperDTO addNewScientificPaper(ScientificPaperDTO newPaper) {
        List<Long> authorsId = Collections.emptyList();
        if (newPaper.getAuthors().isPresent())
            authorsId = newPaper.getAuthors().get().stream().map(author -> author.getId().orElse(null)).filter(Objects::nonNull).toList();
        List<AuthorEntity> authorsById = authorRepository.findAllById(authorsId);
        if (authorsById.size() < authorsId.size())
            throw new NoSuchElementException("Could not find al the authors provided");

        ScientificPaperEntity scientificPaperEntity = scientificPaperTransformer.toEntity(newPaper, authorsById);
        ScientificPaperEntity save = scientificPaperRepository.save(scientificPaperEntity);
        return newPaper;
    }

    ScientificPaperDTO removeScientificPaper(Long id) {
        ScientificPaperEntity scientificPaperEntity = scientificPaperRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("No paper found with id: " + id);
        });
        scientificPaperRepository.delete(scientificPaperEntity);
        return scientificPaperTransformer.toDTO(scientificPaperEntity);
    }

    ScientificPaperDTO updateScientificPaer(Long id, ScientificPaperDTO dto) {
        ScientificPaperEntity entityToUpdate = scientificPaperRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("No paper found with id: " + id);
        });
        return scientificPaperUpdater.update(dto, entityToUpdate);
    }
}
