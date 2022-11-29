package pl.books.app_user;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


@Entity(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String userEmail;
    private String userPassword;
    private String pesel;
    private Boolean isAdult;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<UserRole> userRoles = new ArrayList<>();

    public AppUser() {
    }

    public AppUser(String username, String userEmail, String userPassword, String pesel, Boolean isAdult, Collection<UserRole> userRoles) {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.pesel = pesel;
        this.isAdult = isAdult;
        this.userRoles = userRoles;
    }

    public AppUser(Long id, String username, String userEmail, String userPassword, String pesel, Boolean isAdult, Collection<UserRole> userRoles) {
        this.id = id;
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.pesel = pesel;
        this.isAdult = isAdult;
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", pesel='" + pesel + '\'' +
                ", isAdult=" + isAdult +
                ", userRoles=" + userRoles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) && Objects.equals(username, appUser.username) && Objects.equals(userEmail, appUser.userEmail) && Objects.equals(userPassword, appUser.userPassword) && Objects.equals(pesel, appUser.pesel) && Objects.equals(isAdult, appUser.isAdult) && Objects.equals(userRoles, appUser.userRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userEmail, userPassword, pesel, isAdult, userRoles);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public void setAdult(Boolean adult) {
        isAdult = adult;
    }

    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}