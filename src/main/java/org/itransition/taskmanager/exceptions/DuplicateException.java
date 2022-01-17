package org.itransition.taskmanager.exceptions;

public abstract class DuplicateException extends RuntimeException {

    public DuplicateException(String message) {
        super(message);
    }
}
