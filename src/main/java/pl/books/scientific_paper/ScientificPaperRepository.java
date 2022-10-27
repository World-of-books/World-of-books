package pl.books.scientific_paper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.books.author.Author;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScientificPaperRepository extends JpaRepository<ScientificPaperEntity, Long> {

    List<ScientificPaperEntity> findAllByAuthorsIn(List<Author> authors);

    List<ScientificPaperEntity> findAllByField(FieldOfStudy fieldOfStudy);

    List<ScientificPaperEntity> findAllByPublishedDateBetween(LocalDate start, LocalDate end);

    List<ScientificPaperEntity> findAllByIsForAdults(boolean isForAdults);
}
