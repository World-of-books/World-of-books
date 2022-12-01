package pl.books.app_user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.books.borrow_publication.PublicationType;
import pl.books.scientific_paper.ScientificPaperAuthorDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class BorrowedPublicationDTo {
    private Long id;
    private Long publicationId;
    private String publicationName;
    private List<ScientificPaperAuthorDTO> publicationAuthors;
    private PublicationType publicationType;
    private String borrowDate;
    private String requiredReturnDate;
}
