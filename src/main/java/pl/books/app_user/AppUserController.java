package pl.books.app_user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @DeleteMapping("/delete-user")
    String deleteUser(@RequestBody String username) {
        return appUserService.deleteUser(username);
    }

    @PostMapping
    AppUserCreateNewDto createNewUser(AppUserCreateNewDto dto) {
        return appUserService.saveUser(dto);
    }
}
