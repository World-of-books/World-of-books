package pl.books.borrow_publication;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.books.app.Publication;
import pl.books.app_user.AppUser;
import pl.books.app_user.AppUserRepository;
import pl.books.audiobook.AudiobookRepository;
import pl.books.book.BookEntityRepository;
import pl.books.scientific_paper.ScientificPaperRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BorrowEntityService {

    private final BorrowEntityRepository borrowEntityRepository;
    private final BorrowTransformer borrowTransformer;
    private final AppUserRepository appUserRepository;
    private final ScientificPaperRepository scientificPaperRepository;
    private final AudiobookRepository audiobookRepository;
    private final BookEntityRepository bookEntityRepository;


    List<BorrowDTO> getAllBorrows() {
        return borrowEntityRepository.findAll().stream()
                .map(borrowTransformer::toDto)
                .collect(Collectors.toList());
    }

    List<BorrowDTO> getAllByUserId(String username) {
        return borrowEntityRepository.findAllByAppUser_UsernameOrderByIdDesc(username).stream()
                .map(borrowTransformer::toDto)
                .collect(Collectors.toList());
    }

    List<BorrowDTO> getAllBorrowedByUserId(String username) {
        return borrowEntityRepository.findAllByAppUser_UsernameAndRequiredReturnDateIsGreaterThanEqual(username, LocalDate.now()).stream()
                .map(borrowTransformer::toDto)
                .collect(Collectors.toList());
    }

    BorrowDTO borrowPublication(BorrowRequestDTO dto) {
        AppUser appUser = appUserRepository.findByUsernameIgnoreCase(dto.getUser())
                .orElseThrow(() -> new NoSuchElementException("No user found with username " + dto.getUser()));
        Publication publicationById = getPublicationById(dto.getPublicationId(), dto.getPublicationType());
        borrowEntityRepository.findByAppUser_IdAndPublication_IdAndAndRequiredReturnDateIsGreaterThanEqual(appUser.getId(), publicationById.getId(), LocalDate.now()).ifPresent(borrow -> {
            throw new IllegalArgumentException("Can't borrow the same publication twice at the same time");
        });
        LocalDate borrowendDate = dto.getBorrowEnd() == null ? LocalDate.now().plusDays(7) : LocalDate.parse(dto.getBorrowEnd());
        BorrowEntity borrowEntity = new BorrowEntity(publicationById, dto.getPublicationType(), appUser, LocalDate.now(), borrowendDate);
        return borrowTransformer.toDto(borrowEntityRepository.save(borrowEntity));
    }

    private Publication getPublicationById(Long id, PublicationType publicationType) {
        switch (publicationType) {
            case BOOK -> {
                throw new NoSuchElementException("NOT IMPLEMENTED");
            }
            case SCIENTIFIC_PAPER -> {
                return scientificPaperRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No such paper found"));
            }
            case AUDIOBOOK -> {
                return audiobookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No such audiobook found"));
            }
        }
        throw new NoSuchElementException("No publication was found");
    }
}
