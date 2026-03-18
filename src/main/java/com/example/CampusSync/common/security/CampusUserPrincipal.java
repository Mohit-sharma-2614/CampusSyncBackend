package com.example.CampusSync.common.security;

import com.example.CampusSync.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class CampusUserPrincipal implements UserDetails {

    private final User user;

    public CampusUserPrincipal(User user) {
        this.user = user;
    }

    // --- UserDetails Methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user != null && user.getRole() != null) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        }
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user != null ? user.getPasswordHash() : null;
    }

    @Override
    public String getUsername() {
        return user != null ? user.getEmail() : null;
    }
}
