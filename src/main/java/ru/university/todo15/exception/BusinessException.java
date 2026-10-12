package ru.university.todo15.exception;

public class BusinessException extends RuntimeException {

    public BusinessException() {}

    public BusinessException(String message) {
        super(message);
    }
}
