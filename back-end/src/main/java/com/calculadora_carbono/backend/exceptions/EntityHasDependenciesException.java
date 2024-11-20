package com.calculadora_carbono.backend.exceptions;

public class EntityHasDependenciesException extends RuntimeException{

    public EntityHasDependenciesException(String message){
        super(message);
    }
}
