package com.calculadora_carbono.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    LocalDateTime timestamp;

    Integer code;

    String status;

    List<String> errors;
}
