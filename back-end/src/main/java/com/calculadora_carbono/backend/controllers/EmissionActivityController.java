package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.dtos.EmissionActivityDTO;
import com.calculadora_carbono.backend.dtos.mappers.EmissionActivityMapper;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.entities.EmissionActivity;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.services.CategoryService;
import com.calculadora_carbono.backend.services.EmissionActivityService;
import com.calculadora_carbono.backend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

@RestController
@RequestMapping("/api/emissions")
public class EmissionActivityController {

    @Autowired
    private EmissionActivityService service;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/users/{usersId}/category/{categoryId}/activities")
    public EmissionActivity addActivity(@PathVariable Long usersId, @PathVariable Long categoryId, @RequestBody Double emission){

        Optional<Users> users = usersService.findById(usersId);
        Optional<Category> category = categoryService.findById(categoryId);

        EmissionActivity emissionActivity = new EmissionActivity();
        emissionActivity.setQuantity(emission);

        if(category.isEmpty())
        {
            throw new RuntimeException("Category not found");
        }
        else
        {
            emissionActivity.setCategory(category.get());
        }

        if(users.isEmpty())
        {
            throw new RuntimeException("User not found");
        }
        else
        {
            emissionActivity.setUsers(users.get());
        }

        return service.save(emissionActivity);
    }

    @GetMapping("/users/{usersId}/emissions")
    public Double calculateTotalEmissions(@PathVariable Long usersId) {

        return service.calculateTotalEmissions(usersId);
    }

    @GetMapping("/users/{usersId}/emissions/{categoryId}")
    public Double calculateEmissionsByCategory(@PathVariable Long usersId, @PathVariable Long categoryId) {
        return service.calculateEmissionsByCategory(usersId, categoryId);
    }

    @GetMapping("/users/{usersId}/emissions/day")
    public Double calculateEmissionsPerDay(@PathVariable Long usersId, @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return service.calculateEmissionsPerDay(usersId, day);
    }

    @GetMapping("/users/{usersId}/emissions/period")
    public Double calculateEmissionsPerPeriod(@PathVariable Long usersId,
                                            @RequestParam("startDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDay,
                                            @RequestParam("finalDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finalDay) {
        return service.calculateEmissionsPerPeriod(usersId, startDay, finalDay);
    }


}
