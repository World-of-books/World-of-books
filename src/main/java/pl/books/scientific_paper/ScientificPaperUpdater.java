package pl.books.scientific_paper;

import org.springframework.stereotype.Component;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;

import java.util.*;

@Component
public class ScientificPaperUpdater {

    private final AuthorRepository authorRepository;
    private final ScientificPaperRepository scientificPaperRepository;
    private final ScientificPaperTransformer scientificPaperTransformer;

    public ScientificPaperUpdater(AuthorRepository authorRepository, ScientificPaperRepository scientificPaperRepository, ScientificPaperTransformer scientificPaperTransformer) {
        this.authorRepository = authorRepository;
        this.scientificPaperRepository = scientificPaperRepository;
        this.scientificPaperTransformer = scientificPaperTransformer;
    }

    ScientificPaperDTO update(ScientificPaperDTO dto, ScientificPaperEntity entityToUpdate) {
        List<Long> authorsId = Collections.emptyList();
        if (dto.getAuthors().isPresent())
            authorsId = dto.getAuthors().get().stream().map(author -> author.getId().orElse(null)).filter(Objects::nonNull).toList();
        List<AuthorEntity> authorsById = authorRepository.findAllById(authorsId);
        if (authorsById.size() < authorsId.size())
            throw new NoSuchElementException("Could not find al the authors provided");
        ScientificPaperEntity updateEntity = scientificPaperTransformer.toEntity(dto, authorsById);
        updateName(entityToUpdate, updateEntity);
        updateDescription(entityToUpdate, updateEntity);
        updateField(entityToUpdate, updateEntity);
        updateUniversity(entityToUpdate, updateEntity);
        updateForAdults(entityToUpdate, updateEntity);
        updatePages(entityToUpdate, updateEntity);
        updatePublishDate(entityToUpdate, updateEntity);
        updateAuthors(entityToUpdate, updateEntity);
        scientificPaperRepository.save(entityToUpdate);
        return scientificPaperTransformer.toDTO(entityToUpdate);
    }

    private void updateAuthors(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getAuthors() != entityToUpdate.getAuthors()) {
            Set<AuthorEntity> currentAuthors = entityToUpdate.getAuthors().orElse(Collections.emptySet());
            Set<AuthorEntity> newAuthors = updateEntity.getAuthors().orElse(Collections.emptySet());
            currentAuthors.forEach(auth -> auth.getPublications().ifPresent(list -> list.remove(entityToUpdate)));
            newAuthors.forEach(auth -> auth.getPublications().ifPresent(list -> list.add(entityToUpdate)));
            authorRepository.saveAll(currentAuthors);
            authorRepository.saveAll(newAuthors);
        }
    }

    private void updatePublishDate(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getPublishedDate().isPresent() && updateEntity.getPublishedDate() != entityToUpdate.getPublishedDate())
            entityToUpdate.setPublishedDate(updateEntity.getPublishedDate().get());
    }

    private void updatePages(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getPages().isPresent() && !updateEntity.getPages().equals(entityToUpdate.getPages()))
            entityToUpdate.setPages(updateEntity.getPages().get());
    }

    private void updateForAdults(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getForAdults().isPresent() && updateEntity.getForAdults() != entityToUpdate.getForAdults())
            entityToUpdate.setForAdults(updateEntity.getForAdults().get());
    }

    private void updateUniversity(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getUniversity().isPresent() && !updateEntity.getUniversity().equals(entityToUpdate.getUniversity()))
            entityToUpdate.setUniversity(updateEntity.getUniversity().get());
    }

    private void updateField(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getField().isPresent() && updateEntity.getField() != entityToUpdate.getField())
            entityToUpdate.setField(updateEntity.getField().get());
    }

    private void updateDescription(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getDescription().isPresent() && !updateEntity.getDescription().equals(entityToUpdate.getDescription()))
            entityToUpdate.setDescription(updateEntity.getDescription().get());
    }

    private void updateName(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getName().isPresent() && !updateEntity.getName().equals(entityToUpdate.getName()))
            entityToUpdate.setName(updateEntity.getName().get());
    }
}
