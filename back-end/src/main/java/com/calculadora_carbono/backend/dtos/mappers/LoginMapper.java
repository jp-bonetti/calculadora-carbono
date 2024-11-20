package com.calculadora_carbono.backend.dtos.mappers;

import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.entities.Users;

public class LoginMapper {

    public static LoginDTO toDTO(Users users) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setName(users.getName());
        loginDTO.setEmail(users.getEmail());
        return loginDTO;
    }

    public static Users toEntity(LoginDTO loginDTO) {
        Users login = new Users();
        login.setName(loginDTO.getName());
        login.setEmail(loginDTO.getEmail());
        return login;
    }
}
