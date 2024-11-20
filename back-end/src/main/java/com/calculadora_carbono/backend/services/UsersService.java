package com.calculadora_carbono.backend.services;

import com.calculadora_carbono.backend.entities.EmissionActivity;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.DuplicateEmailException;
import com.calculadora_carbono.backend.exceptions.RequiredFieldNotFoundException;
import com.calculadora_carbono.backend.exceptions.ResourceNotFoundException;
import com.calculadora_carbono.backend.repositories.UsersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    public List<Users> findAll() {
        return repository.findAll();
    }

    public Users findById(Long id) {

        Users users = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return users;
    }

    public void addUsers(Users users) {

        if(users.getName() == null || users.getName().isEmpty()) {
            throw new RequiredFieldNotFoundException("Name is required");
        }

        if(users.getEmail() == null || users.getEmail().isEmpty()) {
            throw new RequiredFieldNotFoundException("Email is required");
        }

        if(repository.findByEmail(users.getEmail()) != null) {
            throw new DuplicateEmailException("Email already exists");
        }

        repository.save(users);
    }
}
