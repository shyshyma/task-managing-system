package org.itransition.taskmanager.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource wasn't found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
