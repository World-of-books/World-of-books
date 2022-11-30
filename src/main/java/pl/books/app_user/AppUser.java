package pl.books.app_user;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.books.app.Publication;
import pl.books.borrow_publication.BorrowEntity;
import pl.books.borrow_publication.PublicationType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@EqualsAndHashCode
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String userEmail;
    private String userPassword;
    private String pesel;
    private LocalDate birthDate;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<UserRole> userRoles = new ArrayList<>();
    @OneToMany(mappedBy = "appUser")
    private List<BorrowEntity> borrow;

    public AppUser() {
    }

    public AppUser(String username, String userEmail, String userPassword, String pesel, LocalDate birthDate, Collection<UserRole> userRoles) {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.pesel = pesel;
        this.birthDate = birthDate;
        this.userRoles = userRoles;
    }

    public AppUser(Long id, String username, String userEmail, String userPassword, String pesel, LocalDate birthDate, Collection<UserRole> userRoles) {
        this.id = id;
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.pesel = pesel;
        this.birthDate = birthDate;
        this.userRoles = userRoles;
    }

}