package com.example.uberprojectauthservice.helpers;

import com.example.uberprojectauthservice.model.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


// why we need this class
// Because spring security works on UserDetails polymorphic type for auth

public class AuthPassengerDetails extends Passenger implements UserDetails{

    private String username;

    private String password;

    public AuthPassengerDetails(Passenger passenger) {
        this.username=passenger.getName();
        this.password=passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
      return  true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
