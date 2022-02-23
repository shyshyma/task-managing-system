package org.itransition.taskmanager.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@Getter
@RequiredArgsConstructor
public enum CacheNames {

    CONSUMER(Constants.CONSUMER_VALUE),
    CONSUMER_EXISTS_BY_ID(Constants.CONSUMER_EXISTS_BY_ID_VALUE),
    CONSUMER_CONFIG(Constants.CONSUMER_CONFIG_VALUE),
    CONSUMER_CONFIG_EMAIL(Constants.CONSUMER_CONFIG_EMAIL_VALUE),
    CONSUMER_CONFIG_EXISTS_BY_ID(Constants.CONSUMER_CONFIG_EXISTS_BY_ID_VALUE),
    CONSUMER_CONFIG_EXISTS_BY_EMAIL(Constants.CONSUMER_CONFIG_EXISTS_BY_EMAIL_VALUE);

    private final String name;

    @UtilityClass
    public final class Constants {

        public static final String CONSUMER_VALUE = "consumer";
        public static final String CONSUMER_EXISTS_BY_ID_VALUE = "consumer-exists-by-id";
        public static final String CONSUMER_CONFIG_VALUE = "consumer-config";
        public static final String CONSUMER_CONFIG_EMAIL_VALUE = "consumer-config-email";
        public static final String CONSUMER_CONFIG_EXISTS_BY_ID_VALUE = "consumer-config-exists-by-id";
        public static final String CONSUMER_CONFIG_EXISTS_BY_EMAIL_VALUE = "consumer-config-exists-by-email";
    }
}
