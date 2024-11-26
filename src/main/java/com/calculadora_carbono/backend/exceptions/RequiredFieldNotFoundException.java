package com.calculadora_carbono.backend.exceptions;

public class RequiredFieldNotFoundException extends RuntimeException{

    public RequiredFieldNotFoundException(String message){
        super(message);
    }
}
