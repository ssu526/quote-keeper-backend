package com.example.quotekeeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String profileImageURL;

    @JsonManagedReference
    @OneToMany(mappedBy = "addedBy")
    private Set<Quote> addedQuotes;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="FAVORITE_QUOTE_ID", referencedColumnName = "quoteId")
    private Quote favoriteQuote;

    @JsonManagedReference
    @ManyToMany(mappedBy = "likedByUsers")
    private Set<Quote> likedQuotes;


    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImageURL = "";
        this.favoriteQuote=null;
        this.addedQuotes = new HashSet<>();
        this.likedQuotes = new HashSet<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Quote getFavoriteQuote() {
        return favoriteQuote;
    }

    public void setFavoriteQuote(Quote favoriteQuote) {
        this.favoriteQuote = favoriteQuote;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public Set<Quote> getAddedQuotes() {
        return addedQuotes;
    }

    public void setAddedQuotes(Set<Quote> addedQuotes) {
        this.addedQuotes = addedQuotes;
    }

    public Set<Quote> getLikedQuotes() {
        return likedQuotes;
    }

    public void setLikedQuotes(Set<Quote> likedQuotes) {
        this.likedQuotes = likedQuotes;
    }
}
