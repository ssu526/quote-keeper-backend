package com.example.quotekeeper.payload;

import com.example.quotekeeper.model.Quote;

public class UserResponse {
    private Long userId;
    private String name;
    private Quote favoriteQuote;
    private String profileImageUrl;

    public UserResponse(Long userId, String name, Quote favoriteQuote, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.favoriteQuote = favoriteQuote;
        this.profileImageUrl = profileImageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quote getFavoriteQuote() {
        return favoriteQuote;
    }

    public void setFavoriteQuote(Quote favoriteQuote) {
        this.favoriteQuote = favoriteQuote;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
