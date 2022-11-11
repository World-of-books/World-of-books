package pl.books.app_user;

import org.springframework.stereotype.Component;
import pl.books.utils.AdultChecker;

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
                new AdultChecker(dto.getPesel()).check(),
                new ArrayList<>()
        );
    }

    AppUserDto toUserDto(AppUser appUser) {
        return new AppUserDto(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getUserEmail(),
                appUser.getUserRoles().stream()
                        .map(UserRole::getRoleName)
                        .collect(Collectors.toList())
        );
    }

}
