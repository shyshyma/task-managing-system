package org.itransition.taskmanager.exception;

public class ModelNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Model with requested ID was" +
            " not found";

    public ModelNotFoundException(long id) {
        super(NOT_FOUND_EXCEPTION_MESSAGE + ", " + id);
    }

    public ModelNotFoundException(int id) {
        super(NOT_FOUND_EXCEPTION_MESSAGE + ", " + id);
    }

    public ModelNotFoundException(String id) {
        super(NOT_FOUND_EXCEPTION_MESSAGE + ", " + id);
    }
}
