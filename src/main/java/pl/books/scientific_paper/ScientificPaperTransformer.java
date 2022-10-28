package pl.books.scientific_paper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScientificPaperTransformer {

    ScientificPaperDTO toDTO(ScientificPaperEntity entity) {
        List<ScientificPaperAuthorDTO> authors = entity.getAuthors().stream()
                .map(author -> new ScientificPaperAuthorDTO(author.getId(), author.getFirstName(), author.getLastName()))
                .toList();
        return new ScientificPaperDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                authors,
                entity.getField(),
                entity.getUniversity(),
                entity.getForAdults(),
                entity.getPages(),
                entity.getPublishedDate()
        );
    }
}
