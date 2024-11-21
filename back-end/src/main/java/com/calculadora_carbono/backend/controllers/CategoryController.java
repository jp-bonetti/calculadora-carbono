package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.auth.JWTService;
import com.calculadora_carbono.backend.dtos.CategoryDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.mappers.CategoryMapper;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService service;
    private final JWTService jwtService;

    @GetMapping
    public List<CategoryDTO> findAll() {

        return service.findAll().stream().map(CategoryMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable Long id) {

        return CategoryMapper.toDTO(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> addCategory(HttpServletRequest request, @RequestBody CategoryDTO categoryDTO) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.addCategory(categoryDTO, usersId);

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
