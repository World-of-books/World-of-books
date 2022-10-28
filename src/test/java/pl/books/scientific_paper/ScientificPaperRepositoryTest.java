package pl.books.scientific_paper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.books.author.Author;
import pl.books.author.AuthorRepository;

import java.time.LocalDate;
import java.util.List;

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
        Author testAuthor = Author.of("TestOne", "Test");
        Author testAuthor2 = Author.of("TestTwo", "Test2");
        testEntityManager.persist(testAuthor);
        testEntityManager.persist(testAuthor2);
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", List.of(testAuthor), FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10));
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", List.of(testAuthor), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12));
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", List.of(testAuthor2), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12));
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);

        //when
        List<ScientificPaperEntity> result = scientificPaperRepository.findAllByAuthorsIn(List.of(testAuthor));

        //then
        assertEquals(List.of(paper1, paper2), result);
    }

    @Test
    void should_return_list_of_all_papers_by_filed_of_study() {
        //given
        Author testAuthor = Author.of("TestOne", "Test");
        Author testAuthor2 = Author.of("TestTwo", "Test2");
        testEntityManager.persist(testAuthor);
        testEntityManager.persist(testAuthor2);
        ScientificPaperEntity paper1 = ScientificPaperEntity.of("First", "Test description", List.of(testAuthor), FieldOfStudy.ASTRONOMY, "Test university", false, 200, LocalDate.of(1999, 10, 10));
        ScientificPaperEntity paper2 = ScientificPaperEntity.of("Second", "Test description2", List.of(testAuthor), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12));
        ScientificPaperEntity paper3 = ScientificPaperEntity.of("Third", "Test description2", List.of(testAuthor2), FieldOfStudy.PHYSIC, "Test university", false, 223, LocalDate.of(1999, 10, 12));
        testEntityManager.persist(paper1);
        testEntityManager.persist(paper2);
        testEntityManager.persist(paper3);

        //when
        List<ScientificPaperEntity> result = scientificPaperRepository.findAllByField(FieldOfStudy.PHYSIC);

        //then
        assertEquals(List.of(paper2, paper3), result);
    }

}