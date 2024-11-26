package com.calculadora_carbono.backend.dtos.mappers;

import com.calculadora_carbono.backend.dtos.EmissionActivityDTO;
import com.calculadora_carbono.backend.entities.EmissionActivity;

public class EmissionActivityMapper {

    public static EmissionActivityDTO toDTO(EmissionActivity emissionActivity) {
        EmissionActivityDTO emissionActivityDTO = new EmissionActivityDTO();
        emissionActivityDTO.setEmissionActivityId(emissionActivity.getId());
        emissionActivityDTO.setQuantity(emissionActivity.getQuantity());
        emissionActivityDTO.setCategoryDTO(CategoryMapper.toDTO(emissionActivity.getCategory()));
        return emissionActivityDTO;
    }

    public static EmissionActivity toEntity(EmissionActivityDTO emissionActivityDTO) {
        EmissionActivity emissionActivity = new EmissionActivity();
        emissionActivity.setId(emissionActivityDTO.getEmissionActivityId());
        emissionActivity.setQuantity(emissionActivityDTO.getQuantity());
        emissionActivity.setCategory(CategoryMapper.toEntity(emissionActivityDTO.getCategoryDTO()));
        return emissionActivity;
    }


}
