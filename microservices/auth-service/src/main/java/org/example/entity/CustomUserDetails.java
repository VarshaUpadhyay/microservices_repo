package org.example.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;

    public CustomUserDetails(Optional<UserCredential> userCred) {
        if (userCred.isPresent()) {
            this.username = userCred.get().getUsername();
            this.password = userCred.get().getPassword();
        }
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
        return username;
    }
}
