package org.itransition.taskmanager.exceptions;


public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException() {
        super("Name already exists, duplicate cannot be!");
    }

    public DuplicateNameException(String message) {
        super(message);
    }
}
