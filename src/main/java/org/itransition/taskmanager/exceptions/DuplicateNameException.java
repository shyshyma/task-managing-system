package org.itransition.taskmanager.exceptions;


public class DuplicateNameException extends DuplicateException {

    private static final String DUPLICATE_NAME_EXCEPTION_MESSAGE =
            "Name already exists, duplicate cannot be!";

    public DuplicateNameException() {
        super(DUPLICATE_NAME_EXCEPTION_MESSAGE);
    }

    public DuplicateNameException(String message) {
        super(DUPLICATE_NAME_EXCEPTION_MESSAGE + " " + message);
    }
}
