package org.itransition.taskmanager.exception;

public class DuplicateTitleException extends DuplicateException {

    private static final String DUPLICATE_TITLE_EXCEPTION_MESSAGE =
            "Such title already exists, duplicate cannot be.";

    public DuplicateTitleException() {
        super(DUPLICATE_TITLE_EXCEPTION_MESSAGE);
    }

    public DuplicateTitleException(String message) {
        super(message);
    }
}
