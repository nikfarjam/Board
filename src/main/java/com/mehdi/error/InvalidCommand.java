package com.mehdi.error;

public class InvalidCommand extends RuntimeException {
    public InvalidCommand(String message) {
        super(message);
    }
}
