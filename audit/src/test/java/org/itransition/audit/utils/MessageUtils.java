package org.itransition.audit.utils;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import org.itransition.audit.mq.AttachedFileLogMessage;
import org.itransition.audit.mq.ConsumerLogMessage;
import org.itransition.audit.mq.TaskLogMessage;

@UtilityClass
public final class MessageUtils {

    private static final Faker FAKER = new Faker();

    public static ConsumerLogMessage generateConsumerLogMessage() {
        ConsumerLogMessage message = new ConsumerLogMessage();
        String name = FAKER.name().firstName();
        String surname = FAKER.name().lastName();
        message.setMessage("'consumer' was created");
        message.setFirstName(name);
        message.setLastName(surname);
        return message;
    }

    public static TaskLogMessage generateTaskLogMessage() {
        TaskLogMessage message = new TaskLogMessage();
        message.setTaskTitle(FAKER.book().title());
        message.setMessage("'task' was created");
        return message;
    }

    public static AttachedFileLogMessage generateAttachedFileLogMessage() {
        AttachedFileLogMessage message = new AttachedFileLogMessage();
        message.setMessage("'attached file' was created");
        message.setFileName(FAKER.file().fileName());
        return message;
    }
}
