package com.teachhub.backend.config;

import com.teachhub.backend.entities.User;
import com.teachhub.backend.repositories.RolesRepository;
import com.teachhub.backend.repositories.UserRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@Nullable String username) throws UsernameNotFoundException{
        User user=userRepository.findUserByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("")
        );
        List<SimpleGrantedAuthority> authorities=user.getRole()
                .stream()
                .map(role->new SimpleGrantedAuthority(role.getRole()))
                .toList();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );

    }
}
