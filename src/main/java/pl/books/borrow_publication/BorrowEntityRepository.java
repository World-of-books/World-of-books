package pl.books.borrow_publication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.books.app_user.AppUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowEntityRepository extends JpaRepository<BorrowEntity, Long> {

    List<BorrowEntity> findAllByAppUser_Id(Long id);

    List<BorrowEntity> findAllByAppUser_UsernameOrderByIdDesc(String username);

    List<BorrowEntity> findAllByAppUser_UsernameAndRequiredReturnDateIsGreaterThanEqual(String username, LocalDate today);

    Optional<BorrowEntity> findByAppUser_IdAndPublication_IdAndAndRequiredReturnDateIsGreaterThanEqual(Long userid, Long publicationId, LocalDate today);
}
