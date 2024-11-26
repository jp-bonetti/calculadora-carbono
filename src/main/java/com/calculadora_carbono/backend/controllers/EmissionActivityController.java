package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.auth.JWTService;
import com.calculadora_carbono.backend.dtos.EmissionActivityDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.QuantityDTO;
import com.calculadora_carbono.backend.dtos.ResultDTO;
import com.calculadora_carbono.backend.dtos.mappers.EmissionActivityMapper;
import com.calculadora_carbono.backend.exceptions.ApiError;
import com.calculadora_carbono.backend.services.EmissionActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "EmissionActivity", description = "Emission activity operations")
public class EmissionActivityController {


    private final EmissionActivityService service;
    private final JWTService jwtService;

    @Operation(summary = "Get all emission activities for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emission activities found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Emission activities not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/activities")
    public ResponseEntity<List<EmissionActivityDTO>> findByUsersId(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        return new ResponseEntity<>(
                service.findByUsersId(usersId).stream().map(EmissionActivityMapper::toDTO).toList(), HttpStatus.OK
        );
    }

    @Operation(summary = "Get emission activities by category for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emission activities found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Emission activities not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/activities/category/{categoryId}")
    public ResponseEntity<List<EmissionActivityDTO>> findByUsersIdAndCategoryId(HttpServletRequest request, @PathVariable Long categoryId) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        return new ResponseEntity<>(
                service.findByUsersIdAndCategoryId(usersId, categoryId).stream().map(EmissionActivityMapper::toDTO).toList(), HttpStatus.OK
        );
    }

    @Operation(summary = "Add a new emission activity for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Emission activity created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping("/category/{categoryId}/activities")
    public ResponseEntity<MessageDTO> addActivity(HttpServletRequest request, @PathVariable Long categoryId, @RequestBody QuantityDTO quantityDTO) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.addActivity(usersId, categoryId, quantityDTO);

        return new ResponseEntity<>(new MessageDTO("Activity added"), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an emission activity for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emission activity deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Emission activity not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/activities/{activityId}")
    public ResponseEntity<MessageDTO> deleteActivity(HttpServletRequest request, @PathVariable Long activityId) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.deleteActivity(usersId, activityId);

        return new ResponseEntity<>(new MessageDTO("Activity deleted"), HttpStatus.OK);
    }

    @Operation(summary = "Update an emission activity for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emission activity updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Emission activity not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/activities/{activityId}")
    public ResponseEntity<MessageDTO> updateActivity(HttpServletRequest request, @PathVariable Long activityId, @RequestBody QuantityDTO quantityDTO) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.updateActivity(usersId, activityId, quantityDTO);

        return new ResponseEntity<>(new MessageDTO("Activity updated"), HttpStatus.OK);
    }

    @Operation(summary = "Calculate total emissions for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total emissions calculated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/total")
    public ResponseEntity<ResultDTO> calculateTotalEmissions(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateTotalEmissions(usersId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Calculate emissions by category for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emissions by category calculated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResultDTO> calculateEmissionsByCategory(HttpServletRequest request, @PathVariable Long categoryId) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateEmissionsByCategory(categoryId, usersId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Calculate emissions per day for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emissions per day calculated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/day")
    public ResponseEntity<ResultDTO> calculateEmissionsPerDay(HttpServletRequest request, @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        ResultDTO result = new ResultDTO();

        result.setResult(service.calculateEmissionsPerDay(usersId, day));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Calculate emissions for a specific period for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emissions for the period calculated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
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
