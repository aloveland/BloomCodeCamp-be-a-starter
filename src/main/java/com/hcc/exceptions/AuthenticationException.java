package com.hcc.exceptions;

public class AuthenticationException extends Exception {

    // Default constructor
    public AuthenticationException() {
        super();
    }

    // Constructor that accepts a message
    public AuthenticationException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}