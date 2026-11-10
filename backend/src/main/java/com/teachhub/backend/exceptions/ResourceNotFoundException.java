package com.teachhub.backend.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("The Requested resource not found");
    }
}
