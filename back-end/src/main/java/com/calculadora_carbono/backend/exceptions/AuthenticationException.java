package com.calculadora_carbono.backend.exceptions;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message){
        super(message);
    }
}
