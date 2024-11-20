package com.calculadora_carbono.backend.services;

import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Optional<Category> findById(Long id) {

        return repository.findById(id);

    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category save(Category category) {
        return repository.save(category);
    }
}
