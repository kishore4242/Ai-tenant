package com.aitenant.auth.exceptions;

public class InvalidCredentialException extends Exception {
    public InvalidCredentialException(String message){
        super(message);
    }
}
