package org.itransition.taskmanager.exception;

public abstract class DuplicateException extends RuntimeException {

    public DuplicateException(String message) {
        super(message);
    }
}
