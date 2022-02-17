package org.itransition.taskmanager.constant;

public final class FreeMarkerTemplatesLocation {

    private FreeMarkerTemplatesLocation() {
        throw new RuntimeException("This class cannot be initialized");
    }

    public static final String SUCCESS_REGISTRATION = "mail/success-registration.ftl";
    public static final String NOTIFICATION = "mail/notification.ftl";
}
