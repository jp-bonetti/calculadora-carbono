package com.calculadora_carbono.backend.auth;

import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.BadRequestException;
import com.calculadora_carbono.backend.exceptions.LoginException;
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
            throw new LoginException("Invalid credentials");
        }

        Users users = usersRepository.findByEmail(loginDTO.getEmail());

        if (users == null || !passwordEncoder.matches(loginDTO.getPassword(), users.getPassword())) {
            throw new LoginException("Invalid credentials");
        }

        return jwtService.generateToken(loginDTO.getEmail(), users.getId());
    }

    public void registerUser(Users users) {

        if (users.getName() == null) {
            throw new BadRequestException("Name is required");
        }

        if (users.getEmail() == null) {
            throw new BadRequestException("Email is required");
        }

        if (usersRepository.findByEmail(users.getEmail()) != null) {
            throw new DuplicateEmailException("Email already exists");
        }

        if (users.getPassword() == null) {
            throw new BadRequestException("Password is required");
        }

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);
    }
}
