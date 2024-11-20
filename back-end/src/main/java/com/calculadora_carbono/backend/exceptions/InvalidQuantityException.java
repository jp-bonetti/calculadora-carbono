package com.calculadora_carbono.backend.exceptions;

public class InvalidQuantityException extends RuntimeException{

    public InvalidQuantityException(String message){
        super(message);
    }
}
