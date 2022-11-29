package pl.books.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.books.app_user.AppUser;
import pl.books.app_user.AppUserRepository;
import pl.books.app_user.UserDetailsAdapter;
import pl.books.error.ErrorEntity;
import pl.books.utils.AdultChecker;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.books.security.EncryptionConfiguration.getAlgorithm;
import static pl.books.utils.DateUtils.setHours;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AppUserRepository appUserRepository) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser credentials;
        String username = null;
        String password = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
            username = credentials.getUsername();
            password = credentials.getUserPassword();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        ErrorEntity<String> error = new ErrorEntity<>();
        error.setCode(FORBIDDEN.value());
        error.setMessage(FORBIDDEN.getReasonPhrase());
        if (failed.getMessage().equals("Bad credentials")) {
            error.setDetails("Wrong password");
        } else {
            error.setDetails(failed.getMessage());
        }
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserDetailsAdapter user = (UserDetailsAdapter) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(setHours(12))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", roles)
                .sign(getAlgorithm());

        Optional<AppUser> userDetails = appUserRepository.findByUsernameIgnoreCase(user.getUsername());
        String pesel = userDetails.map(AppUser::getPesel).orElse(null);

        SuccessfullAuthorizationResponseDTO responseDTO = new SuccessfullAuthorizationResponseDTO(accessToken, LocalDateTime.now().plusHours(12).toString(), roles, user.getUsername(),
                new AdultChecker(pesel).check());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseDTO);

    }
}
