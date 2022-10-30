package pl.books.scientific_paper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class ScientificPaperServiceTest {

    @TestConfiguration
    static class ScientificPaperServiceTestConfig {
        @Bean
        ScientificPaperService scientificPaperService(ScientificPaperRepository scientificPaperRepository, AuthorRepository authorRepository, ScientificPaperTransformer scientificPaperTransformer, ScientificPaperUpdater scientificPaperUpdater, ScientificPaperCommands scientificPaperCommands) {
            return new ScientificPaperService(scientificPaperRepository, authorRepository, scientificPaperTransformer, scientificPaperUpdater, scientificPaperCommands);
        }
    }

    @Autowired
    ScientificPaperService scientificPaperService;
    @MockBean
    ScientificPaperRepository scientificPaperRepository;
    @MockBean
    AuthorRepository authorRepository;
    @MockBean
    ScientificPaperTransformer scientificPaperTransformer;
    @MockBean
    ScientificPaperUpdater scientificPaperUpdater;
    @MockBean
    ScientificPaperCommands scientificPaperCommands;

    @Test
    void adding_new_paper_should_throw_an_exception_if_provided_authors_dont_exist() {
        //given
        ScientificPaperDTO scientificPaperDTO = new ScientificPaperDTO(null, "New Paper", "Some desc", new ArrayList<>(), "ASTRONOMY",
                "University of Tests", false, 123, "1999-9-9");
        ScientificPaperAuthorDTO scientificPaperAuthorDTO = new ScientificPaperAuthorDTO(12L, null, null);
        scientificPaperDTO.getAuthors().get().add(scientificPaperAuthorDTO);
        Mockito.when(authorRepository.findAllById(List.of(12L))).thenReturn(Collections.emptyList());

        //when && then
        assertThrows(NoSuchElementException.class, () -> scientificPaperService.addNewScientificPaper(scientificPaperDTO));
        Mockito.verify(authorRepository).findAllById(List.of(12L));
        Mockito.verify(scientificPaperRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(authorRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void adding_new_paper_should_return_dto_when_successful() {
        //given
        ScientificPaperDTO scientificPaperDTO = new ScientificPaperDTO(null, "New Paper", "Some desc", new ArrayList<>(), "ASTRONOMY",
                "University of Tests", false, 123, "1999-9-9");
        ScientificPaperAuthorDTO scientificPaperAuthorDTO = new ScientificPaperAuthorDTO(12L, null, null);
        scientificPaperDTO.getAuthors().get().add(scientificPaperAuthorDTO);
        AuthorEntity authorEntity = new AuthorEntity(12L, "Test", "Test", new HashSet<>());
        Mockito.when(authorRepository.findAllById(List.of(12L))).thenReturn(List.of(authorEntity));
        ScientificPaperEntity newEntity = new ScientificPaperEntity("New Paper", "Some desc", new HashSet<>(), FieldOfStudy.ASTRONOMY,
                "University of Tests", false, 123, LocalDate.of(1999, 9, 9));
        ScientificPaperDTO expectedDto = new ScientificPaperDTO(null, "New Paper", "Some desc", new ArrayList<>(), "ASTRONOMY",
                "University of Tests", false, 123, "1999-9-9");
        expectedDto.getAuthors().get().add(scientificPaperAuthorDTO);
        Mockito.when(scientificPaperTransformer.toEntity(scientificPaperDTO, List.of(authorEntity))).thenReturn(newEntity);
        Mockito.when(scientificPaperRepository.save(newEntity)).thenReturn(newEntity);
        Mockito.when(scientificPaperTransformer.toDTO(newEntity)).thenReturn(expectedDto);

        //when
        ScientificPaperDTO result = scientificPaperService.addNewScientificPaper(scientificPaperDTO);

        //then
        assertEquals(expectedDto, result);
        assertTrue(newEntity.getAuthors().get().contains(authorEntity));
        assertTrue(authorEntity.getPublications().get().contains(newEntity));
    }

    @Test
    void remove_paper_should_throw_an_exception_when_removing_non_existing_paper(){
        //given
        Long id =  99L;

        //when && then
        assertThrows(NoSuchElementException.class, () -> scientificPaperService.removeScientificPaper(id));
        Mockito.verify(scientificPaperRepository, Mockito.never()).delete(Mockito.any());
        Mockito.verify(authorRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void remove_paper_should_return_dto_if_deleted(){
        //given
        Long id =  99L;
        ScientificPaperEntity entityFound = new ScientificPaperEntity("New Paper", "Some desc", new HashSet<>(), FieldOfStudy.ASTRONOMY,
                "University of Tests", false, 123, LocalDate.of(1999, 9, 9));
        AuthorEntity authorEntity1 = new AuthorEntity(12L, "Test", "Test", new HashSet<>());
        AuthorEntity authorEntity2 = new AuthorEntity(13L, "Test2", "Test2", new HashSet<>());
        entityFound.getAuthors().get().add(authorEntity1);
        entityFound.getAuthors().get().add(authorEntity2);
        authorEntity1.getPublications().get().add(entityFound);
        authorEntity2.getPublications().get().add(entityFound);
        ScientificPaperEntity entityCleard = new ScientificPaperEntity("New Paper", "Some desc", new HashSet<>(), FieldOfStudy.ASTRONOMY,
                "University of Tests", false, 123, LocalDate.of(1999, 9, 9));
        ScientificPaperDTO dto = new ScientificPaperDTO(null, "New Paper", "Some desc", new ArrayList<>(), "ASTRONOMY",
                "University of Tests", false, 123, LocalDate.of(1999, 9, 9).toString());
        Mockito.when(scientificPaperRepository.findById(id)).thenReturn(Optional.of(entityFound));
        Mockito.when(scientificPaperCommands.clearScientificPaperEntity(entityFound)).thenReturn(entityCleard);
        Mockito.when(scientificPaperTransformer.toDTO(entityCleard)).thenReturn(dto);

        //when
        ScientificPaperDTO result = scientificPaperService.removeScientificPaper(id);

        //then
        assertEquals(dto, result);
    }

}