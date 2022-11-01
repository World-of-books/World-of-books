package pl.books.scientific_paper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", Set.of(testAuthor), FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10), 1);
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", Set.of(testAuthor), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12), 1);
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", Set.of(testAuthor2), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12), 1);
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);
        testAuthor.getPublications().add(paper1);
        testAuthor.getPublications().add(paper2);
        testAuthor2.getPublications().add(paper3);

        //when
        Page<ScientificPaperEntity> result = scientificPaperRepository.findAllDistinctByAuthorsIn(List.of(testAuthor), PageRequest.of(0, 5));

        //then
        assertEquals(List.of(paper1, paper2), result.getContent());
    }

    @Test
    void should_return_list_of_all_papers_by_filed_of_study() {
        //given
        AuthorEntity testAuthor = AuthorEntity.of("TestOne", "Test");
        AuthorEntity testAuthor2 = AuthorEntity.of("TestTwo", "Test2");
        testEntityManager.persist(testAuthor);
        testEntityManager.persist(testAuthor2);
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", Set.of(testAuthor), FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10), 1);
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", Set.of(testAuthor), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12), 1);
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", Set.of(testAuthor2), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12), 1);
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);

        //when
        Page<ScientificPaperEntity> result = scientificPaperRepository.findAllByField(FieldOfStudy.PHYSIC, PageRequest.of(0, 5));

        //then
        assertEquals(List.of(paper2, paper3), result.getContent());
    }

    @Test
    void should_return_list_of_all_papers_published_between_two_dates() {
        //given
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", Collections.emptySet(),
                FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10), 1);
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", Collections.emptySet(),
                FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 5, 12), 1);
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", Collections.emptySet(),
                FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 7, 12), 1);
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);

        //when
        Page<ScientificPaperEntity> result = scientificPaperRepository.findAllByPublishedDateBetween(LocalDate.of(1999, 5, 12), LocalDate.of(1999, 7, 12), PageRequest.of(0, 5));

        //then
        assertEquals(List.of(paper2, paper3), result.getContent());
    }

    @Test
    void should_return_list_of_all_papers_meant_for_adults_only() {
        //given
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", Collections.emptySet(),
                FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10), 1);
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", Collections.emptySet(),
                FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 5, 12), 1);
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", Collections.emptySet(),
                FieldOfStudy.PHYSIC, "Test university", true, 223, LocalDate.of(1999, 7, 12), 1);
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);

        //when
        Page<ScientificPaperEntity> result = scientificPaperRepository.findAllByIsForAdults(true, PageRequest.of(0, 5));

        //then
        assertEquals(List.of(paper3), result.getContent());
    }

}