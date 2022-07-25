package com.example.quotekeeper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String quote;

    @Column(nullable = false)
    private LocalDateTime dateAdded;

    @Column(nullable = false)
    private String sourceType;

    @Column(nullable = false)
    private String sourceTitle;

    private String sourceAuthor;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "USER_ID", referencedColumnName = "userId")
    private User addedBy;

    @OneToMany(mappedBy = "favoriteQuote")
    @JsonBackReference
    private Set<User> favoriteByUsers;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "USERS_QUOTES",
            joinColumns = @JoinColumn(name="QUOTE_ID", referencedColumnName = "quoteId"),
            inverseJoinColumns = @JoinColumn(name="USER_ID", referencedColumnName = "userId")
    )
    private Set<User> likedByUsers;

    public Quote() {
    }

    public Quote(String quote, User addedBy, String sourceType, String sourceTitle, String sourceAuthor) {
        this.quote = quote;
        this.dateAdded = LocalDateTime.now();
        this.addedBy = addedBy;
        this.sourceType = sourceType;
        this.sourceTitle = sourceTitle;
        this.sourceAuthor = sourceAuthor;
        this.likedByUsers = new HashSet<>();
        this.favoriteByUsers = new HashSet<>();
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getSourceAuthor() {
        return sourceAuthor;
    }

    public void setSourceAuthor(String sourceAuthor) {
        this.sourceAuthor = sourceAuthor;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public Set<User> getFavoriteByUsers() {
        return favoriteByUsers;
    }

    public void setFavoriteByUsers(Set<User> favoriteByUsers) {
        this.favoriteByUsers = favoriteByUsers;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + quoteId +
                ", quote='" + quote + '\'' +
                ", dateAdded=" + dateAdded +
                ", sourceType='" + sourceType + '\'' +
                ", sourceTitle='" + sourceTitle + '\'' +
                ", sourceAuthor='" + sourceAuthor + '\'' +
                ", addedBy=" + addedBy +
                ", likedByUsers=" + likedByUsers +
                ", favoriteByUsers=" + favoriteByUsers +
                '}';
    }
}
