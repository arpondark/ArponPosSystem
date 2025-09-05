package com.arpon007.ArponPosSystem.service.impl;


import com.arpon007.ArponPosSystem.Repo.UserRepository;
import com.arpon007.ArponPosSystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerUserImplemantation implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User Not Found !!!");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());

        return null;
    }
}
