package com.calculadora_carbono.backend.services;

import com.calculadora_carbono.backend.dtos.QuantityDTO;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.entities.EmissionActivity;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.InvalidActionException;
import com.calculadora_carbono.backend.exceptions.InvalidQuantityException;
import com.calculadora_carbono.backend.exceptions.ResourceNotFoundException;
import com.calculadora_carbono.backend.repositories.EmissionActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmissionActivityService {

    private final EmissionActivityRepository repository;

    private final CategoryService categoryService;

    private final UsersService usersService;

    public void addActivity(Long userId, Long categoryId, QuantityDTO quantityDTO) {

        Users users = usersService.findById(userId);
        Category category = categoryService.findById(categoryId);

        EmissionActivity emissionActivity = new EmissionActivity();

        emissionActivity.setUsers(users);
        emissionActivity.setCategory(category);

        if (quantityDTO.getQuantity() <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than 0");
        }

        emissionActivity.setQuantity(quantityDTO.getQuantity());

        repository.save(emissionActivity);
    }

    public void deleteActivity(Long userId, Long activityId) {

        EmissionActivity activity = repository.findById(activityId).orElseThrow(
                () -> new ResourceNotFoundException("Activity not found")
        );

        log.info("{}", activity);

        if (!activity.getUsers().getId().equals(userId)) {
            throw new InvalidActionException("This user cannot delete this activity");
        }

        log.info("Deleting activity with id {}", activityId);

        repository.deleteById(activityId);
    }

    public void updateActivity(Long usersId, Long id, QuantityDTO quantityDTO) {
        EmissionActivity activity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Activity not found")
        );

        if(!activity.getUsers().getId().equals(usersId)) {
            throw new InvalidActionException("This user cannot update this activity");
        }

        if (quantityDTO.getQuantity() <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than 0");
        }

        activity.setQuantity(quantityDTO.getQuantity());

        repository.save(activity);

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
