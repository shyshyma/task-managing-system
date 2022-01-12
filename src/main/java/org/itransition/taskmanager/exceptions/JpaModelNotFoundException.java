package org.itransition.taskmanager.exceptions;

public class JpaModelNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Entity with requested ID was not found";

    public JpaModelNotFoundException(long id) {
        super(NOT_FOUND_EXCEPTION_MESSAGE + " ID: " + id);
    }

    public JpaModelNotFoundException(int id) {
        super(NOT_FOUND_EXCEPTION_MESSAGE + " ID: " + id);
    }

    public JpaModelNotFoundException(String id) {
        super(NOT_FOUND_EXCEPTION_MESSAGE + " name: " + id);
    }
}
