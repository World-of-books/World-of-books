package pl.books.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.books.author.Author;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {


    List<BookEntity> findAllByAuthorIn(List<Author> author);

    Page<BookEntity> findAllByTitleContainsIgnoreCase(String title, PageRequest pageRequest);

    List<BookEntity> findAllByPublishingHouse(String publishingHouse);

    Optional<BookEntity> findByIsbn(String isbn);


}
