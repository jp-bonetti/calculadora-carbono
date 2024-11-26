package com.calculadora_carbono.backend.exceptions;

public class ResourceNotFoundException extends RuntimeException{

        public ResourceNotFoundException(String message){
            super(message);
        }
}
