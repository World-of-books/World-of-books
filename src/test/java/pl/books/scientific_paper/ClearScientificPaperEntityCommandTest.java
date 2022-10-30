package pl.books.scientific_paper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.books.author.AuthorEntity;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ClearScientificPaperEntityCommandTest {

    @Test
    void clear_entity_should_remove_authors_from_entity_and_entity_from_authors() {
        //given
        ScientificPaperEntity newEntity = new ScientificPaperEntity("New Paper", "Some desc", new HashSet<>(), FieldOfStudy.ASTRONOMY,
                "University of Tests", false, 123, LocalDate.of(1999, 9, 9));
        AuthorEntity authorEntity1 = new AuthorEntity(12L, "Test", "Test", new HashSet<>());
        AuthorEntity authorEntity2 = new AuthorEntity(13L, "Test2", "Test2", new HashSet<>());
        newEntity.getAuthors().add(authorEntity1);
        newEntity.getAuthors().add(authorEntity2);
        authorEntity1.getPublications().get().add(newEntity);
        authorEntity2.getPublications().get().add(newEntity);
        ScientificPaperEntity expectedEntity = new ScientificPaperEntity("New Paper", "Some desc", new HashSet<>(), FieldOfStudy.ASTRONOMY,
                "University of Tests", false, 123, LocalDate.of(1999, 9, 9));

        //when
        ScientificPaperEntity result = new ClearScientificPaperEntityCommand().execute(newEntity);

        //then
        assertEquals(expectedEntity, result);
        assertEquals(0, expectedEntity.getAuthors().size());
        assertEquals(0, authorEntity1.getPublications().get().size());
        assertEquals(0, authorEntity2.getPublications().get().size());

    }

}