package com.calculadora_carbono.backend.auth;

import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.RegisterDTO;
import com.calculadora_carbono.backend.dtos.mappers.RegisterMapper;
import com.calculadora_carbono.backend.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<MessageDTO> login(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(new MessageDTO(authenticationService.authenticate(loginDTO)), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDTO> registerUser(@RequestBody RegisterDTO registerDTO) {
        authenticationService.registerUser(RegisterMapper.toEntity(registerDTO));
        return new ResponseEntity<>(new MessageDTO("User created"), HttpStatus.CREATED);
    }

}
