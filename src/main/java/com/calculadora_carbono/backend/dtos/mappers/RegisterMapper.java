package com.calculadora_carbono.backend.dtos.mappers;

import com.calculadora_carbono.backend.dtos.RegisterDTO;
import com.calculadora_carbono.backend.entities.Users;

public class RegisterMapper {

    public static RegisterDTO toDTO(Users users){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName(users.getName());
        registerDTO.setEmail(users.getEmail());
        registerDTO.setPassword(users.getPassword());
        return registerDTO;
    }

    public static Users toEntity(RegisterDTO registerDTO){
        Users users = new Users();
        users.setName(registerDTO.getName());
        users.setEmail(registerDTO.getEmail());
        users.setPassword(registerDTO.getPassword());
        return users;
    }
}
