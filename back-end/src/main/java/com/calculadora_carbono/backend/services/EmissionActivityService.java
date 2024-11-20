package com.calculadora_carbono.backend.services;

import com.calculadora_carbono.backend.dtos.QuantityDTO;
import com.calculadora_carbono.backend.dtos.ResultDTO;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.entities.EmissionActivity;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.InvalidQuantityException;
import com.calculadora_carbono.backend.exceptions.ResourceNotFoundException;
import com.calculadora_carbono.backend.repositories.EmissionActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class EmissionActivityService {

    @Autowired
    private EmissionActivityRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UsersService usersService;

    public EmissionActivity addActivity(Long userId, Long categoryId, QuantityDTO quantityDTO) {

        Users users = usersService.findById(userId);
        Category category = categoryService.findById(categoryId);

        EmissionActivity emissionActivity = new EmissionActivity();

        emissionActivity.setUsers(users);
        emissionActivity.setCategory(category);

        if (quantityDTO.getQuantity() <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than 0");
        }

        emissionActivity.setQuantity(quantityDTO.getQuantity());

        return repository.save(emissionActivity);
    }

    public Double calculateTotalEmissions(Long userId) {
        List<EmissionActivity> activities = repository.findByUsersId(userId);
        return activities.stream()
                .mapToDouble(activity -> activity.getQuantity() * activity.getCategory().getEmissionFactor())
                .sum();
    }

    public Double calculateEmissionsByCategory(Long userId, Long categoryId) {
        List<EmissionActivity> activities = repository.findByUsersId(userId);
        Category category = categoryService.findById(categoryId);
        return activities.stream()
                .filter(activity -> activity.getCategory() == category)
                .mapToDouble(activity -> activity.getQuantity() * activity.getCategory().getEmissionFactor())
                .sum();
    }

    public Double calculateEmissionsPerDay(Long userId, LocalDate day) {
        List<EmissionActivity> activities = repository.findByUsersId(userId);

        return activities.stream()
                .filter(activity -> activity.getCreationDate().toLocalDate().isEqual(day))
                .mapToDouble(activity -> activity.getQuantity() * activity.getCategory().getEmissionFactor())
                .sum();
    }

    public Double calculateEmissionsPerPeriod(Long userId, LocalDate startDay, LocalDate finalDay) {
        List<EmissionActivity> activities = repository.findByUsersId(userId);

        return activities.stream()
                .filter(activity -> {
                    LocalDate date = activity.getCreationDate().toLocalDate();
                    return !date.isBefore(startDay) && !date.isAfter(finalDay);
                })
                .mapToDouble(activity -> activity.getQuantity() * activity.getCategory().getEmissionFactor())
                .sum();
    }

}
