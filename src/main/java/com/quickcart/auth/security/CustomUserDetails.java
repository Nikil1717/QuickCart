package com.quickcart.auth.security;

import com.quickcart.auth.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails
        implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    
    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority(
                        user.getRole().name()
                )
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public User getUser() {
        return user;
    }
}