package pl.books.borrow_publication;

import pl.books.app.Publication;
import pl.books.app_user.AppUser;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "borrow")
public class BorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Publication publication;
    @OneToOne
    private AppUser appUser;
    private LocalDate borrowDate;
    private LocalDate requiredReturnDate;
    private LocalDate publicationReturnedDate;

    public BorrowEntity() {
    }

    public BorrowEntity(Publication publication, AppUser appUser, LocalDate borrowDate, LocalDate requiredReturnDate, LocalDate publicationReturnedDate) {
        this.publication = publication;
        this.appUser = appUser;
        this.borrowDate = borrowDate;
        this.requiredReturnDate = requiredReturnDate;
        this.publicationReturnedDate = publicationReturnedDate;
    }

    public BorrowEntity(Long id, Publication publication, AppUser appUser, LocalDate borrowDate, LocalDate requiredReturnDate, LocalDate publicationReturnedDate) {
        this.id = id;
        this.publication = publication;
        this.appUser = appUser;
        this.borrowDate = borrowDate;
        this.requiredReturnDate = requiredReturnDate;
        this.publicationReturnedDate = publicationReturnedDate;
    }
}
