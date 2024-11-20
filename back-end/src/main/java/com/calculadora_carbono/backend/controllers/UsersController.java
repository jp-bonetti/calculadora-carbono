package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.dtos.UsersDTO;
import com.calculadora_carbono.backend.dtos.mappers.LoginMapper;
import com.calculadora_carbono.backend.dtos.mappers.UsersMapper;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @GetMapping
    public List<UsersDTO> findAll() {
        return service.findAll().stream().map(UsersMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<UsersDTO>(UsersMapper.toDTO(service.findById(id).get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Users> save(@RequestBody @Valid LoginDTO loginDTO) {

            Users users = service.save(LoginMapper.toEntity(loginDTO));
            return new ResponseEntity<Users>(users, HttpStatus.CREATED);

    }
}
