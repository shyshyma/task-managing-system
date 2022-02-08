package org.itransition.taskmanager.utils;

import com.github.javafaker.Faker;
import org.itransition.taskmanager.mapper.AttachedFileDtoMapper;
import org.itransition.taskmanager.mapper.ConsumerDtoMapper;
import org.itransition.taskmanager.mapper.ConsumerConfigDtoMapper;
import org.itransition.taskmanager.mapper.TaskDtoMapper;
import org.itransition.taskmanager.dto.AttachedFileDto;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.dto.ConsumerConfigDto;
import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.jpa.entity.AttachedFile;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.entity.ConsumerConfig;
import org.itransition.taskmanager.jpa.entity.Task;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Date;

public final class DtoUtils {

    private DtoUtils() {
    }

    private static final Faker FAKER = new Faker();

    private static final ConsumerDtoMapper CONSUMER_DTO_MAPPER =
            Mappers.getMapper(ConsumerDtoMapper.class);

    private static final ConsumerConfigDtoMapper CONSUMER_CONFIG_DTO_MAPPER =
            Mappers.getMapper(ConsumerConfigDtoMapper.class);

    private static final TaskDtoMapper TASK_DTO_MAPPER =
            Mappers.getMapper(TaskDtoMapper.class);

    private static final AttachedFileDtoMapper ATTACHED_FILE_DTO_MAPPER =
            Mappers.getMapper(AttachedFileDtoMapper.class);

    public static ConsumerDto mapToConsumerDto(Consumer consumer, String email) {
        return CONSUMER_DTO_MAPPER.map(consumer, email);
    }

    public static ConsumerConfigDto mapToConsumerConfigDto(ConsumerConfig consumerConfig) {
        return CONSUMER_CONFIG_DTO_MAPPER.map(consumerConfig);
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

    public static ConsumerConfigDto generateConsumerConfigDto() {
        ConsumerConfigDto consumerConfigDto = new ConsumerConfigDto();

        consumerConfigDto.setId(FAKER.number().randomNumber());
        consumerConfigDto.setEmail(FAKER.internet().emailAddress());

        consumerConfigDto.setNotifications(FAKER.bool().bool());
        consumerConfigDto.setNotificationFrequency(NotificationFrequency.EVERY_DAY.toString());
        return consumerConfigDto;
    }

    public static TaskDto generateTaskDto() {
        TaskDto task = new TaskDto();

        task.setStatus("NEW");
        task.setPriority("MEDIUM");

        task.setDonePercentage(FAKER.number().numberBetween(0, 100));

        task.setDescription("task from JPA utils");

        task.setCreationDate(Date.from(Instant.now()));

        task.setExpirationDate(Date.from(Instant.now()));

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
