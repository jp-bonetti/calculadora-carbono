package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.dtos.EmissionActivityDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.QuantityDTO;
import com.calculadora_carbono.backend.dtos.ResultDTO;
import com.calculadora_carbono.backend.dtos.mappers.EmissionActivityMapper;
import com.calculadora_carbono.backend.services.EmissionActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/emissions")
@RequiredArgsConstructor
public class EmissionActivityController {


    private final EmissionActivityService service;

    @GetMapping("/activities/users/{usersId}")
    public ResponseEntity<List<EmissionActivityDTO>> findByUsersId(@PathVariable Long usersId) {

        return new ResponseEntity<>(
                service.findByUsersId(usersId).stream().map(EmissionActivityMapper::toDTO).toList(), HttpStatus.OK
        );
    }

    @GetMapping("/activities/users/{usersId}/category/{categoryId}")
    public ResponseEntity<List<EmissionActivityDTO>> findByUsersIdAndCategoryId(@PathVariable Long usersId, @PathVariable Long categoryId) {

            return new ResponseEntity<>(
                    service.findByUsersIdAndCategoryId(usersId, categoryId).stream().map(EmissionActivityMapper::toDTO).toList(), HttpStatus.OK
            );
    }

    @PostMapping("/users/{usersId}/category/{categoryId}/activities")
    public ResponseEntity<MessageDTO> addActivity(@PathVariable Long usersId, @PathVariable Long categoryId, @RequestBody QuantityDTO quantityDTO) {

        service.addActivity(usersId, categoryId, quantityDTO);

        return new ResponseEntity<>(new MessageDTO("Activity added"), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{usersId}/activities/{activityId}")
    public ResponseEntity<MessageDTO> deleteActivity(@PathVariable Long usersId, @PathVariable Long activityId) {

        service.deleteActivity(usersId, activityId);

        return new ResponseEntity<>(new MessageDTO("Activity deleted"), HttpStatus.OK);
    }

    @PutMapping("/users/{usersId}/activities/{activityId}")
    public ResponseEntity<MessageDTO> updateActivity(@PathVariable Long usersId, @PathVariable Long activityId, @RequestBody QuantityDTO quantityDTO) {

        service.updateActivity(usersId, activityId, quantityDTO);

        return new ResponseEntity<>(new MessageDTO("Activity updated"), HttpStatus.OK);
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
