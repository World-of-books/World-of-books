package pl.books.scientific_paper;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import pl.books.author.AuthorEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TransformingToDtoProviderSuccess implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                //Entity input
                //DTO result
                Arguments.of(
                        new ScientificPaperEntity("Test", "Test", new HashSet<>(),
                                FieldOfStudy.ASTRONOMY, "Test", true, 123, LocalDate.of(1990, 10, 10)),
                        new ScientificPaperDTO(null, "Test", "Test", null,
                                "ASTRONOMY", "Test", true, 123, LocalDate.of(1990, 10, 10).toString())
                ),
                Arguments.of(
                        new ScientificPaperEntity("Test", "Test", Set.of(AuthorEntity.of("Test1", "Test11"), AuthorEntity.of("Test2", "test22")),
                                FieldOfStudy.ASTRONOMY, "Test", true, 123, LocalDate.of(1990, 10, 10)),
                        new ScientificPaperDTO(null, "Test", "Test", List.of(new ScientificPaperAuthorDTO(null, "Test1", "Test11"), new ScientificPaperAuthorDTO(null, "Test2", "test22")),
                                "ASTRONOMY", "Test", true, 123, "1990-10-10")
                )
        );
    }
}
