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
    private final ScientificPaperCommands scientificPaperCommands;

    public ScientificPaperService(ScientificPaperRepository scientificPaperRepository, AuthorRepository authorRepository, ScientificPaperTransformer scientificPaperTransformer, ScientificPaperUpdater scientificPaperUpdater, ScientificPaperCommands scientificPaperCommands) {
        this.scientificPaperRepository = scientificPaperRepository;
        this.authorRepository = authorRepository;
        this.scientificPaperTransformer = scientificPaperTransformer;
        this.scientificPaperUpdater = scientificPaperUpdater;
        this.scientificPaperCommands = scientificPaperCommands;
    }

    Page<ScientificPaperDTO> getAllByExample(Integer page, Integer size, String name, String desc, List<Long> authorsId, String field, String university, Boolean isForAdults) {
        FieldOfStudy fieldOfStudy = null;
        if (field != null) {
            fieldOfStudy = FieldOfStudy.valueOf(field.toUpperCase());
        }
        Set<AuthorEntity> authors = null;
        if (authorsId != null) {
            authors = new HashSet<>(authorRepository.findAllById(authorsId));
        }
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<ScientificPaperEntity> example = Example.of(
                new ScientificPaperEntity(name, desc, authors, fieldOfStudy, university, isForAdults, null, null), matcher);
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
        Page<ScientificPaperEntity> allByAuthorsIn = scientificPaperRepository.findAllDistinctByAuthorsIn(authors, PageRequest.of(page, size));
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
            throw new NoSuchElementException("Could not find provided authors");

        ScientificPaperEntity scientificPaperEntity = scientificPaperTransformer.toEntity(newPaper, authorsById);
        authorsById.forEach(author -> scientificPaperEntity.getAuthors().add(author));
        ScientificPaperEntity save = scientificPaperRepository.save(scientificPaperEntity);
        authorsById.forEach(author -> author.getPublications().ifPresent(publications -> publications.add(save)));
        authorRepository.saveAll(authorsById);
        return scientificPaperTransformer.toDTO(save);
    }

    ScientificPaperDTO removeScientificPaper(Long id) {
        ScientificPaperEntity scientificPaperEntity = scientificPaperRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("No paper found with id: " + id);
        });
        ScientificPaperEntity clearedEntity = scientificPaperCommands.clearScientificPaperEntity(scientificPaperEntity);
        scientificPaperRepository.delete(clearedEntity);
        authorRepository.saveAll(clearedEntity.getAuthors());
        return scientificPaperTransformer.toDTO(clearedEntity);
    }

    ScientificPaperDTO updateScientificPaper(Long id, ScientificPaperDTO dto) {
        ScientificPaperEntity entityToUpdate = scientificPaperRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("No paper found with id: " + id);
        });
        return scientificPaperUpdater.update(dto, entityToUpdate);
    }
}
