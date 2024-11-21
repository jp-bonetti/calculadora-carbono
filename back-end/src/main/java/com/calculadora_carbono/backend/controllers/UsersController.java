package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.auth.JWTService;
import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.UsersDTO;
import com.calculadora_carbono.backend.dtos.mappers.LoginMapper;
import com.calculadora_carbono.backend.dtos.mappers.UsersMapper;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService service;
    private final JWTService jwtService;

    @GetMapping
    public List<UsersDTO> findAll() {
        return new ResponseEntity<>(service.findAll().stream().map(UsersMapper::toDTO).toList(), HttpStatus.OK).getBody();
    }

    @GetMapping("/me")
    public ResponseEntity<UsersDTO> findById(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        return new ResponseEntity<>(UsersMapper.toDTO(service.findById(jwtService.extractUserId(token))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO> deleteUsers(@PathVariable Long id) {

        service.deleteUsers(id);

        return new ResponseEntity<>(new MessageDTO("User deleted"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> updateUsers(@PathVariable Long id, @RequestBody UsersDTO usersDTO) {

        service.updateUsers(id, usersDTO);

        return new ResponseEntity<>(new MessageDTO("User updated"), HttpStatus.OK);
    }
}
