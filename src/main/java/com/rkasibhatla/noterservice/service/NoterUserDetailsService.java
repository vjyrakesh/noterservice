package com.rkasibhatla.noterservice.service;

import com.rkasibhatla.noterservice.entity.User;
import com.rkasibhatla.noterservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NoterUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public NoterUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findUserByUsername(username);
            if(user != null) {
                return org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .accountLocked(!user.isEnabled())
                        .roles(user.getRole())
                        .build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new UsernameNotFoundException(username);
    }
}
