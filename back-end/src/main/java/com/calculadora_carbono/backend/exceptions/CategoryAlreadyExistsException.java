package com.calculadora_carbono.backend.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException{

    public CategoryAlreadyExistsException(String message){
        super(message);
    }
}
