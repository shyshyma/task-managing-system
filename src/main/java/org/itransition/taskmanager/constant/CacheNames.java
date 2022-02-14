package org.itransition.taskmanager.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheNames {

    CONSUMER("consumer"),
    CONSUMER_EXISTS_BY_ID("consumer-exists-by-id"),
    CONSUMER_CONFIG("consumer-config"),
    CONSUMER_CONFIG_EMAIL("consumer-config-email"),
    CONSUMER_CONFIG_EXISTS_BY_EMAIL("consumer-config-exists-by-email");

    private final String name;
}
