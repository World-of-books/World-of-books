package pl.books.scientific_paper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.books.author.AuthorEntity;
import pl.books.author.AuthorRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ScientificPaperRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ScientificPaperRepository scientificPaperRepository;

    @Autowired
    AuthorRepository authorRepository;

    @AfterEach
    void clear() {
        testEntityManager.clear();
    }

    @Test
    void should_return_list_of_all_papers_by_author() {
        //given
        AuthorEntity testAuthor = AuthorEntity.of("TestOne", "Test");
        AuthorEntity testAuthor2 = AuthorEntity.of("TestTwo", "Test2");
        testEntityManager.persist(testAuthor);
        testEntityManager.persist(testAuthor2);
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", Set.of(testAuthor), FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10));
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", Set.of(testAuthor), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12));
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", Set.of(testAuthor2), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12));
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);
        testAuthor.getPublications().add(paper1);
        testAuthor.getPublications().add(paper2);
        testAuthor2.getPublications().add(paper3);

        //when
        List<ScientificPaperEntity> result = scientificPaperRepository.findAllByAuthorsIn(List.of(testAuthor));

        //then
        assertEquals(List.of(paper1, paper2), result);
    }

    @Test
    void should_return_list_of_all_papers_by_filed_of_study() {
        //given
        AuthorEntity testAuthor = AuthorEntity.of("TestOne", "Test");
        AuthorEntity testAuthor2 = AuthorEntity.of("TestTwo", "Test2");
        testEntityManager.persist(testAuthor);
        testEntityManager.persist(testAuthor2);
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", Set.of(testAuthor), FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10));
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", Set.of(testAuthor), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12));
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", Set.of(testAuthor2), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12));
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);

        //when
        List<ScientificPaperEntity> result = scientificPaperRepository.findAllByField(FieldOfStudy.PHYSIC);

        //then
        assertEquals(List.of(paper2, paper3), result);
    }

    @Test
    void should_return_list_of_all_papers_published_between_two_dates() {
        //given
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", Collections.emptySet(),
                FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10));
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", Collections.emptySet(),
                FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 5, 12));
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", Collections.emptySet(),
                FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 7, 12));
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);

        //when
        List<ScientificPaperEntity> result = scientificPaperRepository.findAllByPublishedDateBetween(LocalDate.of(1999, 5, 12), LocalDate.of(1999, 7, 12));

        //then
        assertEquals(List.of(paper2, paper3), result);
    }

    @Test
    void should_return_list_of_all_papers_meant_for_adults_only() {
        //given
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", Collections.emptySet(),
                FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10));
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", Collections.emptySet(),
                FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 5, 12));
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", Collections.emptySet(),
                FieldOfStudy.PHYSIC, "Test university", true, 223, LocalDate.of(1999, 7, 12));
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);

        //when
        List<ScientificPaperEntity> result = scientificPaperRepository.findAllByIsForAdults(true);

        //then
        assertEquals(List.of(paper3), result);
    }

}