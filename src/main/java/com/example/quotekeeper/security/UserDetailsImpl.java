package com.example.quotekeeper.security;

import com.example.quotekeeper.model.Quote;
import com.example.quotekeeper.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String name;
    private String email;

    @JsonIgnore
    private String password;

    private String profileImageURL;
    private Quote favoriteQuote;

    public UserDetailsImpl(Long id, String name, String email, String password, String profileImageURL, Quote favoriteQuote) {
        this.id = id;
        this.name=name;
        this.email=email;
        this.password=password;
        this.profileImageURL=profileImageURL;
        this.favoriteQuote=favoriteQuote;
    }

    public static UserDetailsImpl build(User user){
        return new UserDetailsImpl(user.getUserId(),
                                    user.getName(),
                                    user.getEmail(),
                                    user.getPassword(),
                                    user.getProfileImageURL(),
                                    user.getFavoriteQuote());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getName(){
        return name;
    }

    public String getProfileImageURL(){
        return profileImageURL;
    }

    public Quote getFavoriteQuote(){
        return favoriteQuote;
    }

    public Long getId(){
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
