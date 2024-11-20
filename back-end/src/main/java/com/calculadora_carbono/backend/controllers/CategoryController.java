package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.dtos.CategoryDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.mappers.CategoryMapper;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable Long id) {

        return CategoryMapper.toDTO(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {

        service.addCategory(CategoryMapper.toEntity(categoryDTO));

        return new ResponseEntity<>(new MessageDTO("Category created"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO> deleteCategory(@PathVariable Long id) {

        service.deleteCategory(id);

        return new ResponseEntity<>(new MessageDTO("Category deleted"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {

        service.updateCategory(id, categoryDTO);

        return new ResponseEntity<>(new MessageDTO("Category updated"), HttpStatus.OK);
    }
}
