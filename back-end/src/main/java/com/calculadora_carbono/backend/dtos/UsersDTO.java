package com.calculadora_carbono.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    private Long userId;
    private String name;
    private List<EmissionActivityDTO> emissionActivitiesDTO = new ArrayList<EmissionActivityDTO>();

}
