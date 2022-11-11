package pl.books.security;

import java.util.List;

public class SuccessfullAuthorizationResponseDTO {
    private String accessToken;
    private String accessTokenExpiresAt;
    private List<String> roles;
    private String username;
    private boolean isAdult;

    public SuccessfullAuthorizationResponseDTO(String accessToken, String accessTokenExpiresAt, List<String> roles, String username, boolean isAdult) {
        this.accessToken = accessToken;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.roles = roles;
        this.username = username;
        this.isAdult = isAdult;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdult() {
        return isAdult;
    }
}
