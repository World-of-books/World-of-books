package pl.books.scientific_paper;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import pl.books.author.AuthorEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TransformingToEntitySuccessProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                //DTO input
                //Required list of author entities
                // returned Entity
                Arguments.of(
                        new ScientificPaperDTO(12L, "Test", "test", null,
                                "astronomy", "test", true, 123, "1990-10-10", 1),
                        null,
                        new ScientificPaperEntity("Test", "Test", null,
                                FieldOfStudy.ASTRONOMY, "Test", true, 123, LocalDate.of(1990, 10, 10), 1)
                ),
                Arguments.of(
                        new ScientificPaperDTO(12L, "Test", "test", null,
                                "astronomy", "test", true, 123, "1990-10-10", 1),
                        List.of(AuthorEntity.of("Test1", "Test11"), AuthorEntity.of("Test2", "test22")),
                        new ScientificPaperEntity("Test", "Test", Set.of(AuthorEntity.of("Test1", "Test11"), AuthorEntity.of("Test2", "test22")),
                                FieldOfStudy.ASTRONOMY, "Test", true, 123, LocalDate.of(1990, 10, 10), 1)
                )
        );
    }
}
