package pl.books.borrow_publication;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.books.app.Publication;
import pl.books.app_user.AppUser;
import pl.books.scientific_paper.ScientificPaperEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "borrow")
@Getter
@Setter
@EqualsAndHashCode
public class BorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Publication publication;
    private PublicationType publicationType;
    @ManyToOne
    private AppUser appUser;
    private LocalDate borrowDate;
    private LocalDate requiredReturnDate;

    public BorrowEntity() {
    }

    public BorrowEntity(Long id, Publication publication, PublicationType publicationType, AppUser appUser, LocalDate borrowDate, LocalDate requiredReturnDate) {
        this.id = id;
        this.publication = publication;
        this.publicationType = publicationType;
        this.appUser = appUser;
        this.borrowDate = borrowDate;
        this.requiredReturnDate = requiredReturnDate;
    }

    public BorrowEntity(Publication publication, PublicationType publicationType, AppUser appUser, LocalDate borrowDate, LocalDate requiredReturnDate) {
        this.publication = publication;
        this.publicationType = publicationType;
        this.appUser = appUser;
        this.borrowDate = borrowDate;
        this.requiredReturnDate = requiredReturnDate;
    }

    @Override
    public String toString() {
        return "BorrowEntity{" +
                "id=" + id +
                ", publication=" + publication +
                ", publicationType=" + publicationType +
                ", appUser=" + appUser.getUsername() +
                ", borrowDate=" + borrowDate +
                ", requiredReturnDate=" + requiredReturnDate +
                '}';
    }
}
