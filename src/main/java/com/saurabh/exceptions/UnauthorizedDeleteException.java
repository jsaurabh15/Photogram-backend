package com.saurabh.exceptions;

public class UnauthorizedDeleteException extends RuntimeException {

    public UnauthorizedDeleteException(String message) {
        super(message);
    }
}
