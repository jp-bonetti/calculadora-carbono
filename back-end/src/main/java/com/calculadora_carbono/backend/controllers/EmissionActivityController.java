package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.dtos.EmissionActivityDTO;
import com.calculadora_carbono.backend.dtos.ResultDTO;
import com.calculadora_carbono.backend.dtos.mappers.EmissionActivityMapper;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.entities.EmissionActivity;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.services.CategoryService;
import com.calculadora_carbono.backend.services.EmissionActivityService;
import com.calculadora_carbono.backend.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResultDTO> calculateTotalEmissions(@PathVariable Long usersId) {

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateTotalEmissions(usersId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/users/{usersId}/emissions/{categoryId}")
    public ResponseEntity<ResultDTO> calculateEmissionsByCategory(@PathVariable Long usersId, @PathVariable Long categoryId) {

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateEmissionsByCategory(usersId, categoryId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/users/{usersId}/emissions/day")
    public ResponseEntity<ResultDTO> calculateEmissionsPerDay(@PathVariable Long usersId, @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateEmissionsPerDay(usersId, day));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/users/{usersId}/emissions/period")
    public ResponseEntity<ResultDTO> calculateEmissionsPerPeriod(@PathVariable Long usersId,
                                                                 @RequestParam("startDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDay,
                                                                 @RequestParam("finalDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finalDay) {

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateEmissionsPerPeriod(usersId, startDay, finalDay));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
