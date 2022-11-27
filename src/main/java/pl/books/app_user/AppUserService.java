package pl.books.app_user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final AppUserTransformer appUserTransformer;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository userRepository, UserRoleRepository roleRepository, AppUserTransformer appUserTransformer, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.appUserTransformer = appUserTransformer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new NoSuchElementException("No user found with username " + username));
        return new UserDetailsAdapter(appUser);
    }

    public AppUserDto saveUser(AppUserCreateNewDto user) {
        userRepository.findByUserEmailIgnoreCase(user.getUserEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Another user already exists with email address " + user.getUserEmail());
        });
        userRepository.findByUsernameIgnoreCase(user.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Another user already exists with username " + user.getUsername());
        });
        userRepository.findByPesel(user.getPesel()).ifPresent(u -> {
            throw new IllegalArgumentException("Another user already exists with provided PESEL");
        });

        AppUser appUserEntity = appUserTransformer.createNewDtoToEntity(user);
        appUserEntity.setUserPassword(passwordEncoder.encode(user.getPassword()));
        UserRole userRole = roleRepository.findByRoleNameIgnoreCase("user").get();
        appUserEntity.getUserRoles().add(userRole);
        AppUser savedUser = userRepository.save(appUserEntity);
        return appUserTransformer.toUserDto(savedUser);
    }

    String deleteUser(String username) {
        AppUser appUser = userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new NoSuchElementException("No user found with username " + username));
        userRepository.delete(appUser);
        return username;
    }

    public Page<AppUserDto> getAllUsers(Integer page, Integer size) {
        page = page == null || page < 0 ? 0 : page;
        size = size == null || size < 1 ? 100 : size;
        Page<AppUser> results = userRepository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "username")));
        return results == null ? Page.empty() : results.map(appUserTransformer::toUserDto);
    }

    public AppUserDto getUserById(Long id) {
        AppUser appUser = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No user found with id " + id));
        return appUserTransformer.toUserDto(appUser);
    }

}
