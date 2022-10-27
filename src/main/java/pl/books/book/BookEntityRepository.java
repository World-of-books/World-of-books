package pl.books.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.books.author.Author;
import pl.books.book.BookEntity;
import pl.books.scientific_paper.FieldOfStudy;
import pl.books.scientific_paper.ScientificPaperEntity;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface BookEntityRepository extends JpaRepository<BookEntity,Integer> {



    List<BookEntity> findAllByAuthorIn(List<Author> author);

    List<BookEntity> findAllByTitle(String title);

    List<BookEntity> findAllByPublishingHouse(String publishingHouse);



}
