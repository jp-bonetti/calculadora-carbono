package com.calculadora_carbono.backend.dtos.mappers;

import com.calculadora_carbono.backend.dtos.CategoryDTO;
import com.calculadora_carbono.backend.dtos.CreateCategoryDTO;
import com.calculadora_carbono.backend.entities.Category;

public class CreateCategoryMapper {
    public static CreateCategoryDTO toDTO(Category category) {
        CreateCategoryDTO categoryDTO = new CreateCategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setEmissionFactor(category.getEmissionFactor());
        return categoryDTO;
    }

    public static Category toEntity(CreateCategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setEmissionFactor(categoryDTO.getEmissionFactor());
        return category;
    }
}
