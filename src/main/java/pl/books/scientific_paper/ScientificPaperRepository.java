package pl.books.scientific_paper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.books.author.AuthorEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScientificPaperRepository extends JpaRepository<ScientificPaperEntity, Long> {

    Page<ScientificPaperEntity> findAllByAuthorsIn(List<AuthorEntity> authors, PageRequest pageRequest);

    Page<ScientificPaperEntity> findAllByField(FieldOfStudy fieldOfStudy, PageRequest pageRequest);

    Page<ScientificPaperEntity> findAllByPublishedDateBetween(LocalDate start, LocalDate end, PageRequest pageRequest);

    Page<ScientificPaperEntity> findAllByIsForAdults(boolean isForAdults, PageRequest pageRequest);
}
