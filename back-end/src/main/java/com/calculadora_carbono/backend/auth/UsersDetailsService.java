package com.calculadora_carbono.backend.auth;

import com.calculadora_carbono.backend.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username){
        return (UserDetails) repository.findByEmail(username);
    }
}
