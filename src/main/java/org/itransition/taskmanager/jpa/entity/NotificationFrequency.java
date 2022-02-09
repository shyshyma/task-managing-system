package org.itransition.taskmanager.jpa.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationFrequency {

    EVERY_DAY("0 0 10 * * *"),
    EVERY_WEEK("0 0 10 * * MON"),
    EVERY_MONTH("0 0 10 1 * *"),
    EVERY_YEAR("0 0 10 1 JAN *");

    private final String cronExpression;
}
