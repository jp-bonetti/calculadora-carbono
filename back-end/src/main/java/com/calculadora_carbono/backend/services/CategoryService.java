package com.calculadora_carbono.backend.services;

import com.calculadora_carbono.backend.dtos.CategoryDTO;
import com.calculadora_carbono.backend.dtos.CreateCategoryDTO;
import com.calculadora_carbono.backend.dtos.mappers.CategoryMapper;
import com.calculadora_carbono.backend.dtos.mappers.CreateCategoryMapper;
import com.calculadora_carbono.backend.dtos.mappers.UsersMapper;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.CategoryAlreadyExistsException;
import com.calculadora_carbono.backend.exceptions.EntityHasDependenciesException;
import com.calculadora_carbono.backend.exceptions.ResourceNotFoundException;
import com.calculadora_carbono.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {


    private final CategoryRepository repository;

    private final UsersService usersService;

    public Category findById(Long id, Long userId) {

        Users users = usersService.findById(userId);

        return repository.findByIdAndUsers(id, users).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    }

    public List<Category> findByUsersId(Long usersId) {
        return repository.findByUsersId(usersId);
    }

    public void addCategory(CreateCategoryDTO categoryDTO, Long usersId) {

        Users users = usersService.findById(usersId);

        Category category = CreateCategoryMapper.toEntity(categoryDTO);

        category.setUsers(users);

        repository.findByNameAndUsers(category.getName(), users).ifPresent(c -> {
            throw new CategoryAlreadyExistsException("Category already exists");
        });

        repository.save(category);
    }

    public void deleteCategory(Long id, Long usersId ){

        repository.findByIdAndUsers(id, usersService.findById(usersId))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        try
        {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new EntityHasDependenciesException("Category has dependencies");
        }


    }

    public void updateCategory(Long id, CreateCategoryDTO categoryDTO, Long usersId) {

        Users users = usersService.findById(usersId);

        Category category = this.findById(id, usersId);

        category.setUsers(users);

        repository.findByNameAndUsers(category.getName(), users).ifPresent(c -> {
            throw new CategoryAlreadyExistsException("Category already exists");
        });

        category.setId(id);
        category.setName(categoryDTO.getName());
        category.setEmissionFactor(categoryDTO.getEmissionFactor());

        repository.save(category);
    }
}
