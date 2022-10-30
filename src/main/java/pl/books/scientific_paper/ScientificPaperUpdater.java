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
        if (updateEntity.getAuthors() != null && updateEntity.getAuthors().size() > 0 && updateEntity.getAuthors() != entityToUpdate.getAuthors()) {
            Set<AuthorEntity> currentAuthors = entityToUpdate.getAuthors();
            Set<AuthorEntity> newAuthors = updateEntity.getAuthors();
            currentAuthors.forEach(auth -> auth.getPublications().ifPresent(list -> list.remove(entityToUpdate)));
            newAuthors.forEach(auth -> auth.getPublications().ifPresent(list -> list.add(entityToUpdate)));
            entityToUpdate.setAuthors(newAuthors);
            if (currentAuthors.size() > 0)
                authorRepository.saveAll(currentAuthors);
            authorRepository.saveAll(newAuthors);
        }
    }

    private void updatePublishDate(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getPublishedDate() !=null && updateEntity.getPublishedDate() != entityToUpdate.getPublishedDate())
            entityToUpdate.setPublishedDate(updateEntity.getPublishedDate());
    }

    private void updatePages(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getPages() !=null && !updateEntity.getPages().equals(entityToUpdate.getPages()))
            entityToUpdate.setPages(updateEntity.getPages());
    }

    private void updateForAdults(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getForAdults() !=null && updateEntity.getForAdults() != entityToUpdate.getForAdults())
            entityToUpdate.setForAdults(updateEntity.getForAdults());
    }

    private void updateUniversity(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getUniversity() !=null && !updateEntity.getUniversity().equals(entityToUpdate.getUniversity()))
            entityToUpdate.setUniversity(updateEntity.getUniversity());
    }

    private void updateField(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getField() !=null && updateEntity.getField() != entityToUpdate.getField())
            entityToUpdate.setField(updateEntity.getField());
    }

    private void updateDescription(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getDescription() !=null && !updateEntity.getDescription().equals(entityToUpdate.getDescription()))
            entityToUpdate.setDescription(updateEntity.getDescription());
    }

    private void updateName(ScientificPaperEntity entityToUpdate, ScientificPaperEntity updateEntity) {
        if (updateEntity.getName() !=null && !updateEntity.getName().equals(entityToUpdate.getName()))
            entityToUpdate.setName(updateEntity.getName());
    }
}
