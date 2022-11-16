package pl.books.author;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void should_return_list_of_all_authors_found() {
        //given
        AuthorEntity first = AuthorEntity.of("test", "test");
        AuthorEntity second = AuthorEntity.of("test", "test");
        testEntityManager.persist(first);
        testEntityManager.persist(second);

        //when
        List<AuthorEntity> result = authorRepository.findAllByIdIn(List.of(1L, 2L));

        //then
        assertEquals(List.of(first, second), result);
    }

}