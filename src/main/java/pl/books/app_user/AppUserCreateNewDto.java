package pl.books.app_user;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;


public class AppUserCreateNewDto {
    @NotBlank
    private String username;
    @Email
    private String userEmail;
    private String password;
    private String pesel;

    public AppUserCreateNewDto(String username, String userEmail, String password, String pesel) {
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "AppUserCreateNewDto{" +
                "username='" + username + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUserCreateNewDto)) return false;
        AppUserCreateNewDto that = (AppUserCreateNewDto) o;
        return Objects.equals(username, that.username) && Objects.equals(userEmail, that.userEmail) && Objects.equals(password, that.password) && Objects.equals(pesel, that.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userEmail, password, pesel);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
}
