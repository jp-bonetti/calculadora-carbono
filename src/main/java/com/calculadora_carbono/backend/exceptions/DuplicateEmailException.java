package com.calculadora_carbono.backend.exceptions;

public class DuplicateEmailException extends RuntimeException{

    public DuplicateEmailException(String message){
        super(message);
    }
}
