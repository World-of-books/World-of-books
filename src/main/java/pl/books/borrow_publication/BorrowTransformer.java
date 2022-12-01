package pl.books.borrow_publication;

import org.springframework.stereotype.Service;
import pl.books.app.Publication;
import pl.books.app_user.AppUser;
import pl.books.audiobook.AudiobookEntity;
import pl.books.scientific_paper.ScientificPaperAuthorDTO;
import pl.books.scientific_paper.ScientificPaperEntity;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@Service
public class BorrowTransformer {

    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();

    BorrowDTO toDto(BorrowEntity entity) {
        Publication publication = entity.getPublication();
        AppUser appUser = entity.getAppUser();
        List<ScientificPaperAuthorDTO> authors = publication.getAuthors().stream()
                .map(author -> new ScientificPaperAuthorDTO(
                        author.getId() != null ? author.getId() : null, author.getFirstName() != null ? author.getFirstName() : null, author.getLastName() != null ? author.getLastName() : null))
                .toList();
        return new BorrowDTO(
                entity.getId(),
                publication.getId(),
                publication.getName(),
                authors,
                getPublicationType(publication),
                appUser.getId(),
                appUser.getUsername(),
                appUser.getUserEmail(),
                entity.getBorrowDate().toString(),
                entity.getRequiredReturnDate().toString()
        );
    }

    PublicationType getPublicationType(Publication publication) {
        if (publication instanceof ScientificPaperEntity)
            return PublicationType.SCIENTIFIC_PAPER;
        else if (publication instanceof AudiobookEntity)
            return PublicationType.AUDIOBOOK;
        else return PublicationType.BOOK;
    }
}
