package com.calculadora_carbono.backend.services;

import com.calculadora_carbono.backend.dtos.CategoryDTO;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.exceptions.CategoryAlreadyExistsException;
import com.calculadora_carbono.backend.exceptions.EntityHasDependenciesException;
import com.calculadora_carbono.backend.exceptions.ResourceNotFoundException;
import com.calculadora_carbono.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public void addCategory(Category category) {

        repository.findByName(category.getName()).ifPresent(c -> {
            throw new CategoryAlreadyExistsException("Category already exists");
        });

        repository.save(category);
    }

    public void deleteCategory(Long id){

        this.findById(id);

        try
        {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new EntityHasDependenciesException("Category has dependencies");
        }


    }

    public void updateCategory(Long id, CategoryDTO categoryDTO) {

        Category category = this.findById(id);

        category.setId(id);
        category.setName(categoryDTO.getName());
        category.setEmissionFactor(categoryDTO.getEmissionFactor());

        repository.save(category);
    }
}
