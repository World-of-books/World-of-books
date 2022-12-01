package pl.books.app_user;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    Page<AppUserDto> getAllUsers(@RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size) {
        return appUserService.getAllUsers(page, size);
    }

    @DeleteMapping("/delete-user")
    String deleteUser(@RequestBody String username) {
        return appUserService.deleteUser(username);
    }

    @PostMapping
    AppUserDto createNewUser(@RequestBody AppUserCreateNewDto dto) {
        return appUserService.saveUser(dto);
    }
}
