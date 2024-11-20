package com.calculadora_carbono.backend.dtos.mappers;

import com.calculadora_carbono.backend.dtos.UsersDTO;
import com.calculadora_carbono.backend.entities.Users;

public class UsersMapper {

    public static UsersDTO toDTO(Users users) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUserId(users.getId());
        usersDTO.setName(users.getName());
        usersDTO.setEmail(users.getEmail());
        usersDTO.getEmissionActivitiesDTO().addAll(users.getEmissionActivities().stream().map(EmissionActivityMapper::toDTO).toList());
        return usersDTO;
    }

    public static Users toEntity(UsersDTO usersDTO) {
        Users users = new Users();
        users.setId(usersDTO.getUserId());
        users.setName(usersDTO.getName());
        users.setEmail(usersDTO.getEmail());
        users.getEmissionActivities().addAll(usersDTO.getEmissionActivitiesDTO().stream().map(EmissionActivityMapper::toEntity).toList());
        return users;
    }
}
