package pl.books.scientific_paper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ScientificPaperUpdaterTest {

    @TestConfiguration
    static class UpdaterTestConfig {
        @Bean
        ScientificPaperUpdater scientificPaperUpdater(AuthorRepository authorRepository, ScientificPaperRepository scientificPaperRepository, ScientificPaperTransformer scientificPaperTransformer) {
            return new ScientificPaperUpdater(authorRepository, scientificPaperRepository, scientificPaperTransformer);
        }
    }

    @Autowired
    ScientificPaperUpdater scientificPaperUpdater;
    @MockBean
    AuthorRepository authorRepository;
    @MockBean
    ScientificPaperRepository scientificPaperRepository;
    @MockBean
    ScientificPaperTransformer scientificPaperTransformer;

    @Test
    void updater_should_throw_an_exception_when_authors_not_found() {
        //given
        ScientificPaperEntity entityToUpdate = new ScientificPaperEntity("Test", "Test", null,
                FieldOfStudy.ASTRONOMY, "Test", true, 123, LocalDate.of(1990, 10, 10));
        ScientificPaperDTO dto = new ScientificPaperDTO(null, "Test", "Test", List.of(new ScientificPaperAuthorDTO(1L, "test", "test"), new ScientificPaperAuthorDTO(34L, "test2", "testt")),
                "ASTRONOMY", "Test", true, 123, LocalDate.of(1990, 10, 10).toString());

        Mockito.when(authorRepository.findAllById(List.of(1L, 34L))).thenReturn(Collections.emptyList());

        //when && then
        assertThrows(NoSuchElementException.class, () -> scientificPaperUpdater.update(dto, entityToUpdate));
        Mockito.verify(scientificPaperRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(authorRepository, Mockito.never()).saveAll(Mockito.any());
    }

    @Test
    void update_should_add_new_author() {
        //given
        ScientificPaperDTO updateDTO = new ScientificPaperDTO(null, null, null, List.of(new ScientificPaperAuthorDTO(99L, "Test1", "Test1"), new ScientificPaperAuthorDTO(100L, "Test2", "Test2")), null, null, null, null, null);
        AuthorEntity existingAuthor = new AuthorEntity(99L, "Test1", "Test1", new HashSet<>());
        AuthorEntity newAuthor = new AuthorEntity(100L, "Test2", "Test2", new HashSet<>());
        ScientificPaperEntity entityToUpdate = new ScientificPaperEntity("Test Name", "Some desc", new HashSet<>(), null, null, null, null, null);
        existingAuthor.getPublications().get().add(entityToUpdate);
        entityToUpdate.getAuthors().get().add(existingAuthor);
        ScientificPaperEntity entityOfUpdateDTO = new ScientificPaperEntity(null, null, new HashSet<>(), null, null, null, null, null);
        entityOfUpdateDTO.getAuthors().get().add(existingAuthor);
        entityOfUpdateDTO.getAuthors().get().add(newAuthor);
        ScientificPaperDTO expectedDTO = new ScientificPaperDTO(null, "Test Name", "Some desc", new ArrayList<>(), null, null, null, null, null);
        expectedDTO.getAuthors().get().add(new ScientificPaperAuthorDTO(99L, "Test1", "Test1"));
        expectedDTO.getAuthors().get().add(new ScientificPaperAuthorDTO(100L, "Test2", "Test2"));
        Mockito.when(authorRepository.findAllById(List.of(99L, 100L))).thenReturn(List.of(existingAuthor, newAuthor));
        Mockito.when(scientificPaperTransformer.toEntity(updateDTO, List.of(existingAuthor, newAuthor))).thenReturn(entityOfUpdateDTO);
        Mockito.when(scientificPaperTransformer.toDTO(entityToUpdate)).thenReturn(expectedDTO);

        //when
        ScientificPaperDTO updateResult = scientificPaperUpdater.update(updateDTO, entityToUpdate);

        //then
        assertEquals(expectedDTO, updateResult);
        assertTrue(existingAuthor.getPublications().get().contains(entityToUpdate));
        assertTrue(newAuthor.getPublications().get().contains(entityToUpdate));
        assertTrue(entityToUpdate.getAuthors().get().contains(existingAuthor));
        assertTrue(entityToUpdate.getAuthors().get().contains(newAuthor));
    }

    @Test
    void updater_should_remove_existing_author_and_add_another_one() {
        //given
        ScientificPaperDTO updateDTO = new ScientificPaperDTO(null, null, null, List.of(new ScientificPaperAuthorDTO(100L, "Test2", "Test2")), null, null, null, null, null);
        AuthorEntity existingAuthor = new AuthorEntity(99L, "Test1", "Test1", new HashSet<>());
        AuthorEntity newAuthor = new AuthorEntity(100L, "Test2", "Test2", new HashSet<>());
        ScientificPaperEntity entityToUpdate = new ScientificPaperEntity("Test Name", "Some desc", new HashSet<>(), null, null, null, null, null);
        existingAuthor.getPublications().get().add(entityToUpdate);
        entityToUpdate.getAuthors().get().add(existingAuthor);
        ScientificPaperEntity entityOfUpdateDTO = new ScientificPaperEntity(null, null, new HashSet<>(), null, null, null, null, null);
        entityOfUpdateDTO.getAuthors().get().add(newAuthor);
        ScientificPaperDTO expectedDTO = new ScientificPaperDTO(null, "Test Name", "Some desc", new ArrayList<>(), null, null, null, null, null);
        expectedDTO.getAuthors().get().add(new ScientificPaperAuthorDTO(100L, "Test2", "Test2"));
        Mockito.when(authorRepository.findAllById(List.of(100L))).thenReturn(List.of(newAuthor));
        Mockito.when(scientificPaperTransformer.toEntity(updateDTO, List.of(newAuthor))).thenReturn(entityOfUpdateDTO);
        Mockito.when(scientificPaperTransformer.toDTO(entityToUpdate)).thenReturn(expectedDTO);

        //when
        ScientificPaperDTO updateResult = scientificPaperUpdater.update(updateDTO, entityToUpdate);

        //then
        assertEquals(expectedDTO, updateResult);
        assertTrue(newAuthor.getPublications().get().contains(entityToUpdate));
        assertEquals(0, existingAuthor.getPublications().get().size());
        assertEquals(1, entityToUpdate.getAuthors().get().size());
        assertTrue(entityToUpdate.getAuthors().get().contains(newAuthor));
        Mockito.verify(authorRepository, Mockito.times(2)).saveAll(Mockito.any());

    }

    @ParameterizedTest
    @ArgumentsSource(UpdaterSuccessArgumentsProvider.class)
    void updater_should_return_updated_value(ScientificPaperDTO updateDto, ScientificPaperEntity entityToUpdate,
                                             List<AuthorEntity> authorsMocked, ScientificPaperEntity entityOfUpdateDTO,
                                             ScientificPaperEntity expectedResult, ScientificPaperDTO expectedResultDTO) {
        entityToUpdate.getAuthors().get().forEach(auth -> auth.getPublications().ifPresent(pub -> pub.add(entityToUpdate)));
        Mockito.when(authorRepository.findAllById(Mockito.any())).thenReturn(authorsMocked);
        Mockito.when(scientificPaperTransformer.toEntity(updateDto, authorsMocked)).thenReturn(entityOfUpdateDTO);
        Mockito.when(scientificPaperTransformer.toDTO(entityToUpdate)).thenReturn(expectedResultDTO);

        //when
        ScientificPaperDTO updateResult = scientificPaperUpdater.update(updateDto, entityToUpdate);

        //then
        assertEquals(expectedResultDTO, updateResult);
        assertEquals(expectedResult, entityToUpdate);
        assertEquals(expectedResult.getAuthors().get().size(), entityToUpdate.getAuthors().get().size());
        Mockito.verify(scientificPaperRepository).save(entityToUpdate);
    }
}