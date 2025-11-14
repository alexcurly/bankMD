package com.bank.history.exception;

public class NoEntityWithThisIdException extends RuntimeException {
    public NoEntityWithThisIdException(String massage) {
        super(massage);
    }

    public NoEntityWithThisIdException() {
        super("Id was not found");
    }
}
