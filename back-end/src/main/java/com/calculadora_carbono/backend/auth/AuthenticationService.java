package com.calculadora_carbono.backend.auth;

import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.AuthenticationException;
import com.calculadora_carbono.backend.exceptions.DuplicateEmailException;
import com.calculadora_carbono.backend.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JWTService jwtService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(LoginDTO loginDTO) {

        if (loginDTO.getEmail() == null || loginDTO.getPassword() == null) {
            throw new AuthenticationException("Invalid credentials");
        }

        Users users = usersRepository.findByEmail(loginDTO.getEmail());

        if (users == null || !passwordEncoder.matches(loginDTO.getPassword(), users.getPassword())) {
            throw new AuthenticationException("Invalid credentials");
        }

        return jwtService.generateToken(loginDTO.getEmail());
    }

    public void registerUser(Users users) {

        if (users.getName() == null) {
            throw new AuthenticationException("Name is required");
        }

        if (users.getEmail() == null) {
            throw new AuthenticationException("Email is required");
        }

        if (usersRepository.findByEmail(users.getEmail()) != null) {
            throw new DuplicateEmailException("Email already exists");
        }

        if (users.getPassword() == null) {
            throw new AuthenticationException("Password is required");
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);
    }
}
