package org.itransition.taskmanager.utils;

import com.github.javafaker.Faker;
import org.itransition.taskmanager.jpa.entity.Priority;
import org.itransition.taskmanager.jpa.entity.Status;
import org.itransition.taskmanager.mapper.jpa.AttachedFileJpaMapper;
import org.itransition.taskmanager.mapper.jpa.ConsumerJpaMapper;
import org.itransition.taskmanager.mapper.jpa.TaskJpaMapper;
import org.itransition.taskmanager.dto.AttachedFileDto;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.jpa.entity.AttachedFile;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.entity.Task;
import org.mapstruct.factory.Mappers;

import java.util.Date;

public final class JpaUtils {

    private JpaUtils(){
    }

    private static final Faker FAKER = new Faker();

    private static final ConsumerJpaMapper CONSUMER_JPA_MAPPER =
            Mappers.getMapper(ConsumerJpaMapper.class);

    private static final TaskJpaMapper TASK_JPA_MAPPER =
            Mappers.getMapper(TaskJpaMapper.class);

    private static final AttachedFileJpaMapper ATTACHED_FILE_JPA_MAPPER =
            Mappers.getMapper(AttachedFileJpaMapper.class);

    public static Consumer mapToConsumer(ConsumerDto consumerDto) {
        return CONSUMER_JPA_MAPPER.map(consumerDto);
    }

    public static Task mapToTask(TaskDto consumerDto) {
        return TASK_JPA_MAPPER.map(consumerDto);
    }

    public static AttachedFile mapToAttachedFile(AttachedFileDto attachedFileDto) {
       return ATTACHED_FILE_JPA_MAPPER.map(attachedFileDto);
    }

    public static Consumer generateConsumer() {
        Consumer consumer = new Consumer();

        consumer.setId(FAKER.number().randomNumber());

        consumer.setFirstName(FAKER.name().firstName());
        consumer.setSecondName(FAKER.name().lastName());

        consumer.setEmail(FAKER.internet().emailAddress());

        consumer.setDateOfBirth(FAKER.date().birthday());
        return consumer;
    }

    public static Task generateTask() {
        Task task = new Task();

        task.setStatus(Status.NEW);
        task.setPriority(Priority.MEDIUM);

        task.setDonePercentage(FAKER.number().numberBetween(0, 100));

        task.setDescription("task from JPA utils");

        int creationDateIntervalStart = FAKER.number().numberBetween(1000, 5000);
        int creationDateIntervalEnd = FAKER.number().numberBetween(6000, 9000);
        task.setCreationDate(FAKER.date().between(new Date(creationDateIntervalStart),
                new Date(creationDateIntervalEnd)));

        int expirationDate = FAKER.number().numberBetween(10000, 15000);
        task.setExpirationDate(new Date(expirationDate));

        task.setId(FAKER.number().randomNumber());

        task.setTitle(FAKER.name().title());
        return task;
    }

    public static AttachedFile generateAttachedFile() {
        AttachedFile attachedFile = new AttachedFile();

        attachedFile.setId(FAKER.number().randomNumber());

        attachedFile.setName(FAKER.file().fileName());
        attachedFile.setData(new byte[]{4, 16, 44, 33, 77, 13, 62});

        return attachedFile;
    }
}
