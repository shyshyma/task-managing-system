package org.itransition.taskmanager.exceptions;

public class DuplicateEmailException extends DuplicateException {

    private static final String DUPLICATE_EMAIL_EXCEPTION_MESSAGE =
            "Email already exists, duplicate cannot be!";

    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL_EXCEPTION_MESSAGE);
    }

    public DuplicateEmailException(String message) {
        super(DUPLICATE_EMAIL_EXCEPTION_MESSAGE + " " + message);
    }
}
