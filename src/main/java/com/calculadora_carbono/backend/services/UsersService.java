package com.calculadora_carbono.backend.services;

import com.calculadora_carbono.backend.dtos.UsersDTO;
import com.calculadora_carbono.backend.entities.EmissionActivity;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.DuplicateEmailException;
import com.calculadora_carbono.backend.exceptions.EntityHasDependenciesException;
import com.calculadora_carbono.backend.exceptions.RequiredFieldNotFoundException;
import com.calculadora_carbono.backend.exceptions.ResourceNotFoundException;
import com.calculadora_carbono.backend.repositories.UsersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    public Users findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void deleteUsers(Long id) {

        this.findById(id);

        try
        {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new EntityHasDependenciesException("User has dependencies");
        }

    }

    public void updateUsers(Long id, UsersDTO usersDTO){

            Users users = this.findById(id);

            users.setId(id);

            if(usersDTO.getName() != null && !usersDTO.getName().isEmpty())
            {
                users.setName(usersDTO.getName());
            }
            else
            {
                throw new RequiredFieldNotFoundException("Name is required");
            }

            if(usersDTO.getEmail() != null && !usersDTO.getEmail().isEmpty())
            {
                users.setEmail(usersDTO.getEmail());
            }
            else
            {
                throw new RequiredFieldNotFoundException("Email is required");
            }

            repository.save(users);
    }
}
