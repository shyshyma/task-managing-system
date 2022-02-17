package org.itransition.taskmanager.exception;

public class DuplicateFileNameException extends DuplicateException {

    private static final String DUPLICATE_FILE_NAME_EXCEPTION_MESSAGE =
            "File with such name already exists, duplicate cannot be presented!";

    public DuplicateFileNameException() {
        super(DUPLICATE_FILE_NAME_EXCEPTION_MESSAGE);
    }

    public DuplicateFileNameException(String message) {
        super(message);
    }
}
