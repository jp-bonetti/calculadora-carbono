package com.calculadora_carbono.backend.dtos.mappers;

import com.calculadora_carbono.backend.dtos.CategoryDTO;
import com.calculadora_carbono.backend.entities.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setEmissionFactor(category.getEmissionFactor());
        return categoryDTO;
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getCategoryId());
        category.setName(categoryDTO.getName());
        category.setEmissionFactor(categoryDTO.getEmissionFactor());
        return category;
    }
}
