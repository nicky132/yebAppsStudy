package com.xxxx.springsecurityoauth2demo.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorites;

    public User(String username, String password, List<GrantedAuthority> authorites) {
        this.username = username;
        this.password = password;
        this.authorites = authorites;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorites;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
