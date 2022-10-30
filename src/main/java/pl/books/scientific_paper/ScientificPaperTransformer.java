package pl.books.scientific_paper;

import org.springframework.stereotype.Service;
import pl.books.author.AuthorEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashSet;
import java.util.List;

@Service
public class ScientificPaperTransformer {

    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();

    ScientificPaperDTO toDTO(ScientificPaperEntity entity) {
        List<ScientificPaperAuthorDTO> authors = entity.getAuthors().stream()
                .map(author -> new ScientificPaperAuthorDTO(
                        author.getId().orElse(null), author.getFirstName().orElse(null), author.getLastName().orElse(null)))
                .toList();
        return new ScientificPaperDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                authors,
                entity.getField().name(),
                entity.getUniversity(),
                entity.getForAdults(),
                entity.getPages(),
                entity.getPublishedDate() == null ? null : entity.getPublishedDate().toString()
        );
    }

    ScientificPaperEntity toEntity(ScientificPaperDTO dto, List<AuthorEntity> authors) {
        HashSet<AuthorEntity> authorEntitiesSet = authors == null ? null : new HashSet<>(authors);
        return new ScientificPaperEntity(
                dto.getName().map(this::capitalize).orElse(null),
                dto.getDescription().map(this::capitalize).orElse(null),
                authorEntitiesSet,
                dto.getField().map(this::parseFieldOfStudy).orElse(null),
                dto.getUniversity().map(this::capitalize).orElse(null),
                dto.getForAdults().orElse(null),
                dto.getPages().orElse(null),
                dto.getPublishedDate().map(this::parseDate).orElse(null)
        );
    }

    private FieldOfStudy parseFieldOfStudy(String field) {
        try {
            return FieldOfStudy.valueOf(field.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid field of study");
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format, provide yyyy-MM-dd");
        }
    }

    private String capitalize(String string) {
        StringBuilder result = new StringBuilder();
        string = string.replaceAll("( )+", " ");
        String[] split = string.split(" ");
        for (int i = 0; i < split.length; i++) {
            result.append(split[i].substring(0, 1).toUpperCase()).append(split[i].substring(1).toLowerCase());
            if (i + 1 < split.length && split[i + 1] != null) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}
