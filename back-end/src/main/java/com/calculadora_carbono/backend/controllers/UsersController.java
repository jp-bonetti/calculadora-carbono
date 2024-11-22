package com.calculadora_carbono.backend.controllers;

import com.calculadora_carbono.backend.auth.JWTService;
import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.UsersDTO;
import com.calculadora_carbono.backend.dtos.mappers.LoginMapper;
import com.calculadora_carbono.backend.dtos.mappers.UsersMapper;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.ApiError;
import com.calculadora_carbono.backend.services.UsersService;
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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users operations")
public class UsersController {

    private final UsersService service;
    private final JWTService jwtService;

    @Operation(summary = "Get user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public ResponseEntity<UsersDTO> findById(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        return new ResponseEntity<>(UsersMapper.toDTO(service.findById(jwtService.extractUserId(token))), HttpStatus.OK);
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping
    public ResponseEntity<MessageDTO> deleteUsers(HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);

        Long id = jwtService.extractUserId(token);

        service.deleteUsers(id);

        return new ResponseEntity<>(new MessageDTO("User deleted"), HttpStatus.OK);
    }

    @Operation(summary = "Update user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping
    public ResponseEntity<MessageDTO> updateUsers(HttpServletRequest request, @RequestBody UsersDTO usersDTO) {

        String token = request.getHeader("Authorization").substring(7);

        Long id = jwtService.extractUserId(token);

        service.updateUsers(id, usersDTO);

        return new ResponseEntity<>(new MessageDTO("User updated"), HttpStatus.OK);
    }
}
