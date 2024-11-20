package com.calculadora_carbono.backend.exceptions;

public class InvalidActionException extends RuntimeException{

    public InvalidActionException(String message){
        super(message);
    }
}
