package pl.books.app_user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.books.borrow_publication.BorrowEntity;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class AppUserDto {

    private Long id;
    private String username;
    private String userEmail;
    private boolean isAdult;
    private List<String> userRoles;
    private List<BorrowedPublicationDTo> borrow;

}
