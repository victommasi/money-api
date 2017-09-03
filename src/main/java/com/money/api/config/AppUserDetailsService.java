package com.money.api.config;

import com.money.api.model.User;
import com.money.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional =  userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User and/or Password wrong"));
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), getRoles(user));
    }

    private Collection<? extends GrantedAuthority> getRoles(User user) {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        user.getRoles().forEach(r -> authoritySet.add(new SimpleGrantedAuthority(r.getDescription().toUpperCase())));
        return authoritySet;
    }
}
