package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.auth.JWTService;
import com.calculadora_carbono.backend.dtos.EmissionActivityDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.QuantityDTO;
import com.calculadora_carbono.backend.dtos.ResultDTO;
import com.calculadora_carbono.backend.dtos.mappers.EmissionActivityMapper;
import com.calculadora_carbono.backend.services.EmissionActivityService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JWTService jwtService;

    @GetMapping("/activities")
    public ResponseEntity<List<EmissionActivityDTO>> findByUsersId(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        return new ResponseEntity<>(
                service.findByUsersId(usersId).stream().map(EmissionActivityMapper::toDTO).toList(), HttpStatus.OK
        );
    }

    @GetMapping("/activities/category/{categoryId}")
    public ResponseEntity<List<EmissionActivityDTO>> findByUsersIdAndCategoryId(HttpServletRequest request, @PathVariable Long categoryId) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        return new ResponseEntity<>(
                service.findByUsersIdAndCategoryId(usersId, categoryId).stream().map(EmissionActivityMapper::toDTO).toList(), HttpStatus.OK
        );
    }

    @PostMapping("/category/{categoryId}/activities")
    public ResponseEntity<MessageDTO> addActivity(HttpServletRequest request, @PathVariable Long categoryId, @RequestBody QuantityDTO quantityDTO) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.addActivity(usersId, categoryId, quantityDTO);

        return new ResponseEntity<>(new MessageDTO("Activity added"), HttpStatus.CREATED);
    }

    @DeleteMapping("/activities/{activityId}")
    public ResponseEntity<MessageDTO> deleteActivity(HttpServletRequest request, @PathVariable Long activityId) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.deleteActivity(usersId, activityId);

        return new ResponseEntity<>(new MessageDTO("Activity deleted"), HttpStatus.OK);
    }

    @PutMapping("/activities/{activityId}")
    public ResponseEntity<MessageDTO> updateActivity(HttpServletRequest request, @PathVariable Long activityId, @RequestBody QuantityDTO quantityDTO) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.updateActivity(usersId, activityId, quantityDTO);

        return new ResponseEntity<>(new MessageDTO("Activity updated"), HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<ResultDTO> calculateTotalEmissions(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateTotalEmissions(usersId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResultDTO> calculateEmissionsByCategory(HttpServletRequest request, @PathVariable Long categoryId) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateEmissionsByCategory(categoryId, usersId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/day")
    public ResponseEntity<ResultDTO> calculateEmissionsPerDay(HttpServletRequest request, @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateEmissionsPerDay(usersId, day));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/period")
    public ResponseEntity<ResultDTO> calculateEmissionsPerPeriod(HttpServletRequest request,
                                                                 @RequestParam("startDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDay,
                                                                 @RequestParam("finalDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finalDay) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateEmissionsPerPeriod(usersId, startDay, finalDay));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
