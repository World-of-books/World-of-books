package pl.books.audiobook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudiobookRepository extends JpaRepository<AudiobookEntity, Long> {
}
