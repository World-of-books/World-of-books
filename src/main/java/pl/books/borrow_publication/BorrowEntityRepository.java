package pl.books.borrow_publication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowEntityRepository extends JpaRepository<BorrowEntity, Long> {
}
