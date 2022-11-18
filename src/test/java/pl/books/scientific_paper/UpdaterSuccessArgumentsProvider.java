package pl.books.scientific_paper;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import pl.books.author.AuthorEntity;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class UpdaterSuccessArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                // Update DTO
                // Entity to update
                // List<AuthorEntity> mocked authors
                // Entity of transformed Update DTO
                // Expected updated Entity for Assertions
                // DTO of transformed entity to be returned
                Arguments.of(
                        new ScientificPaperDTO(null, "New Name", null, null, null, null, null, null, null, null),
                        new ScientificPaperEntity("Test Name", "Some desc", Set.of(new AuthorEntity("Test Author", "Test Name")), null, null, null, null, null, null),
                        Collections.emptyList(),
                        new ScientificPaperEntity("New Name", null, null, null, null, null, null, null, null),
                        new ScientificPaperEntity("New Name", "Some desc", Set.of(new AuthorEntity("Test Author", "Test Name")), null, null, null, null, null, null),
                        new ScientificPaperDTO(null, "New Name", "Some desc", List.of(new ScientificPaperAuthorDTO(99L, "Test Author", "Test Name")), null, null, null, null, null, null)
                ),
                Arguments.of(
                        new ScientificPaperDTO(null, null, "New desc!", null, null, null, null, null, null, null),
                        new ScientificPaperEntity("Test Name", "Some desc", Set.of(new AuthorEntity("Test Author", "Test Name")), null, null, null, null, null, null),
                        Collections.emptyList(),
                        new ScientificPaperEntity(null, "New desc!", null, null, null, null, null, null, null),
                        new ScientificPaperEntity("Test Name", "New desc!", Set.of(new AuthorEntity("Test Author", "Test Name")), null, null, null, null, null, null),
                        new ScientificPaperDTO(null, "Test Name", "New desc!", List.of(new ScientificPaperAuthorDTO(99L, "Test Author", "Test Name")), null, null, null, null, null, null)
                ),
                Arguments.of(
                        new ScientificPaperDTO(null, null, null, null, null, null, null, null, null, null),
                        new ScientificPaperEntity("Test Name", "Some desc", Set.of(new AuthorEntity("Test Author", "Test Name")), null, null, null, null, null, null),
                        Collections.emptyList(),
                        new ScientificPaperEntity(null, null, null, null, null, null, null, null, null),
                        new ScientificPaperEntity("Test Name", "Some desc", Set.of(new AuthorEntity("Test Author", "Test Name")), null, null, null, null, null, null),
                        new ScientificPaperDTO(null, "Test Name", "Some desc", List.of(new ScientificPaperAuthorDTO(99L, "Test Author", "Test Name")), null, null, null, null, null, null)
                ),
                Arguments.of(
                        new ScientificPaperDTO(null, null, null, null, "astronomy", null, null, null, null, null),
                        new ScientificPaperEntity("Test Name", "Some desc", Set.of(new AuthorEntity("Test Author", "Test Name")), null, null, null, null, null, null),
                        Collections.emptyList(),
                        new ScientificPaperEntity(null, null, null, FieldOfStudy.ASTRONOMY, null, null, null, null, null),
                        new ScientificPaperEntity("Test Name", "Some desc", Set.of(new AuthorEntity("Test Author", "Test Name")), FieldOfStudy.ASTRONOMY, null, null, null, null, null),
                        new ScientificPaperDTO(null, "Test Name", "Some desc", List.of(new ScientificPaperAuthorDTO(99L, "Test Author", "Test Name")), "ASTRONOMY", null, null, null, null, null)
                )
        );

    }
}
