package ru.sumarokov.task_tracker.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {};

    public AccessDeniedException(String message) {
        super(message);
    }
}
