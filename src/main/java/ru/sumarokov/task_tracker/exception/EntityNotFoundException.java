package ru.sumarokov.task_tracker.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {}

    public EntityNotFoundException(String message) {
        super(message);
    }
}
