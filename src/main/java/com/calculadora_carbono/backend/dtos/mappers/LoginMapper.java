package com.calculadora_carbono.backend.dtos.mappers;

import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.entities.Users;

public class LoginMapper {

    public static LoginDTO toDTO(Users users) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(users.getEmail());
        loginDTO.setPassword(users.getPassword());
        return loginDTO;
    }

    public static Users toEntity(LoginDTO loginDTO) {
        Users login = new Users();
        login.setEmail(loginDTO.getEmail());
        login.setPassword(loginDTO.getPassword());
        return login;
    }
}
