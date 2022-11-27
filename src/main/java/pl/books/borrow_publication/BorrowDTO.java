package pl.books.borrow_publication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.books.scientific_paper.ScientificPaperAuthorDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BorrowDTO {
    private Long id;
    private Long publicationId;
    private String publicationName;
    private List<ScientificPaperAuthorDTO> publicationAuthors;
    private PublicationType publicationType;
    private Long userId;
    private String userName;
    private String userEmail;
    private String borrowDate;
    private String requiredReturnDate;
}
