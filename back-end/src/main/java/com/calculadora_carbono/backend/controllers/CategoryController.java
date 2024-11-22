package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.auth.JWTService;
import com.calculadora_carbono.backend.dtos.CategoryDTO;
import com.calculadora_carbono.backend.dtos.CreateCategoryDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.mappers.CategoryMapper;
import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.exceptions.ApiError;
import com.calculadora_carbono.backend.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category operations")
public class CategoryController {


    private final CategoryService service;
    private final JWTService jwtService;

    @Operation(summary = "Get categories by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Categories not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public List<CategoryDTO> findByUsersId(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        return service.findByUsersId(usersId).stream().map(CategoryMapper::toDTO).toList();
    }

    @Operation(summary = "Get category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public CategoryDTO findById(HttpServletRequest request, @PathVariable Long id) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        return CategoryMapper.toDTO(service.findById(id, usersId));
    }

    @Operation(summary = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<MessageDTO> addCategory(HttpServletRequest request, @RequestBody CreateCategoryDTO categoryDTO) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.addCategory(categoryDTO, usersId);

        return new ResponseEntity<>(new MessageDTO("Category created"), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO> deleteCategory(HttpServletRequest request, @PathVariable Long id) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.deleteCategory(id, usersId);

        return new ResponseEntity<>(new MessageDTO("Category deleted"), HttpStatus.OK);
    }

    @Operation(summary = "Update a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> updateCategory(HttpServletRequest request, @PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {

        String token = request.getHeader("Authorization").substring(7);

        Long usersId = jwtService.extractUserId(token);

        service.updateCategory(id, categoryDTO, usersId);

        return new ResponseEntity<>(new MessageDTO("Category updated"), HttpStatus.OK);
    }
}
