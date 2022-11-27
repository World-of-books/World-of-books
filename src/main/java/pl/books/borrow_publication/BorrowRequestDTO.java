package pl.books.borrow_publication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class BorrowRequestDTO {
    private Long publicationId;
    private PublicationType publicationType;
    private String user;
    private String borrowEnd;
}
