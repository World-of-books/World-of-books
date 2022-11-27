package pl.books.app_user;

import org.springframework.stereotype.Component;
import pl.books.utils.AdultChecker;
import pl.books.utils.BirthDateChecker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Component
public class AppUserTransformer {

    AppUser createNewDtoToEntity(AppUserCreateNewDto dto) {
        return new AppUser(
                dto.getUsername(),
                dto.getUserEmail(),
                dto.getPassword(),
                dto.getPesel(),
                new BirthDateChecker(dto.getPesel()).getBirthDateFromPesel(),
                new ArrayList<>()
        );
    }

    AppUserDto toUserDto(AppUser appUser) {
        LocalDate now = LocalDate.now();
        boolean isAdult = appUser.getBirthDate().plusYears(18).isBefore(now.plusDays(1));
        return new AppUserDto(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getUserEmail(),
                isAdult,
                appUser.getUserRoles().stream()
                        .map(UserRole::getRoleName)
                        .collect(Collectors.toList())
        );
    }

}
