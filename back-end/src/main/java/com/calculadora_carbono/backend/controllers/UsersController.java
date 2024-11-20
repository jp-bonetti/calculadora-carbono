package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.UsersDTO;
import com.calculadora_carbono.backend.dtos.mappers.LoginMapper;
import com.calculadora_carbono.backend.dtos.mappers.UsersMapper;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.services.UsersService;
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

    @GetMapping
    public List<UsersDTO> findAll() {
        return new ResponseEntity<>(service.findAll().stream().map(UsersMapper::toDTO).toList(), HttpStatus.OK).getBody();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(UsersMapper.toDTO(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageDTO> addUsers(@RequestBody LoginDTO loginDTO) {

            service.addUsers(LoginMapper.toEntity(loginDTO));

            return new ResponseEntity<>(new MessageDTO("User created"), HttpStatus.CREATED);

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
