package com.calculadora_carbono.backend.auth;

import com.calculadora_carbono.backend.dtos.LoginDTO;
import com.calculadora_carbono.backend.dtos.MessageDTO;
import com.calculadora_carbono.backend.dtos.RegisterDTO;
import com.calculadora_carbono.backend.dtos.mappers.RegisterMapper;
import com.calculadora_carbono.backend.entities.Users;
import com.calculadora_carbono.backend.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication operations")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<MessageDTO> login(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(new MessageDTO(authenticationService.authenticate(loginDTO)), HttpStatus.OK);
    }

    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<MessageDTO> registerUser(@RequestBody RegisterDTO registerDTO) {
        authenticationService.registerUser(RegisterMapper.toEntity(registerDTO));
        return new ResponseEntity<>(new MessageDTO("User created"), HttpStatus.CREATED);
    }

}
