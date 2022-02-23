package org.itransition.audit.utils;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import org.itransition.audit.mq.TaskLogMessage;

@UtilityClass
public final class MessageUtils {

    private static final Faker FAKER = new Faker();

    public static TaskLogMessage generateTaskLogMessage() {
        TaskLogMessage message = new TaskLogMessage();
        message.setTaskTitle(FAKER.book().title());
        message.setMessage(FAKER.app().name() + " was created");
        return message;
    }
}
