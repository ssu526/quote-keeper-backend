package com.example.quotekeeper.payload;

import com.example.quotekeeper.model.Quote;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String name;
    private String email;
    private String profileImageURL;
    private Quote favoriteQuote;
    private Long userId;

    public JwtResponse(String accessToken, String name, String email, String profileImageURL, Quote favoriteQuote, Long userId) {
        this.token = accessToken;
        this.name = name;
        this.email = email;
        this.profileImageURL=profileImageURL;
        this.favoriteQuote=favoriteQuote;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public Quote getFavoriteQuote() {
        return favoriteQuote;
    }

    public void setFavoriteQuote(Quote favoriteQuote) {
        this.favoriteQuote = favoriteQuote;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
