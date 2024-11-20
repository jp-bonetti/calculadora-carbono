package com.calculadora_carbono.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersDTO {

    private Long userId;
    private String name;
    private String email;
    private List<EmissionActivityDTO> emissionActivitiesDTO = new ArrayList<>();

}
