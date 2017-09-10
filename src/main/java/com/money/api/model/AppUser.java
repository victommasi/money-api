package com.money.api.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;

import java.util.Collection;

public class AppUser extends org.springframework.security.core.userdetails.User{

    private static final long serialVersionUID = 1L;

    private User user;

    public AppUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getName(), user.getPassword(), authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
