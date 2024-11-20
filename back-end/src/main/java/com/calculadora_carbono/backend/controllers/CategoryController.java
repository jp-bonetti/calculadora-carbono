package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.dtos.CategoryDTO;
import com.calculadora_carbono.backend.dtos.mappers.CategoryMapper;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public List<CategoryDTO> findAll() {
        return service.findAll().stream().map(CategoryMapper::toDTO).toList();
    }

    @PostMapping
    public Category save(@RequestBody CategoryDTO categoryDTO) {
        return service.save(CategoryMapper.toEntity(categoryDTO));
    }
}
