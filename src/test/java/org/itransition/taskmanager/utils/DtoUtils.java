package org.itransition.taskmanager.utils;

import com.github.javafaker.Faker;
import org.itransition.taskmanager.mapper.dto.AttachedFileDtoMapper;
import org.itransition.taskmanager.mapper.dto.ConsumerDtoMapper;
import org.itransition.taskmanager.mapper.dto.TaskDtoMapper;
import org.itransition.taskmanager.dto.AttachedFileDto;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.jpa.entity.AttachedFile;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.entity.Task;
import org.mapstruct.factory.Mappers;

import java.util.Date;

public final class DtoUtils {

    private DtoUtils() {
    }

    private static final Faker FAKER = new Faker();

    private static final ConsumerDtoMapper CONSUMER_DTO_MAPPER =
            Mappers.getMapper(ConsumerDtoMapper.class);

    private static final TaskDtoMapper TASK_DTO_MAPPER =
            Mappers.getMapper(TaskDtoMapper.class);

    private static final AttachedFileDtoMapper ATTACHED_FILE_DTO_MAPPER =
            Mappers.getMapper(AttachedFileDtoMapper.class);

    public static ConsumerDto mapToConsumerDto(Consumer consumer) {
        return CONSUMER_DTO_MAPPER.map(consumer);
    }

    public static TaskDto mapToTaskDto(Task task) {
        return TASK_DTO_MAPPER.map(task);
    }

    public static AttachedFileDto mapToFileDto(AttachedFile attachedFile) {
        return ATTACHED_FILE_DTO_MAPPER.map(attachedFile);
    }

    public static ConsumerDto generateConsumerDto() {
        ConsumerDto consumer = new ConsumerDto();
        
        consumer.setId(FAKER.number().randomNumber());

        consumer.setName(FAKER.name().firstName());
        consumer.setSurname(FAKER.name().lastName());

        consumer.setEmail(FAKER.internet().emailAddress());

        consumer.setDateOfBirth(FAKER.date().birthday());
        return consumer;
    }

    public static TaskDto generateTaskDto() {
        TaskDto task = new TaskDto();

        task.setStatus("NEW");
        task.setPriority("MEDIUM");

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

    public static AttachedFileDto generateAttachedFileDto() {
        AttachedFileDto attachedFileDto = new AttachedFileDto();

        attachedFileDto.setId(FAKER.number().randomNumber());
        attachedFileDto.setName(FAKER.file().fileName());
        attachedFileDto.setContent("TWluaW1hbGx5IGNvbmZpZ3VyZWQgdGhlIHByb2p");

        return attachedFileDto;
    }
}
