package org.itransition.taskmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.constant.FreeMarkerTemplatesLocation;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.event.SuccessRegistrationEvent;
import org.itransition.taskmanager.exception.DuplicateEmailException;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.jpa.entity.ConsumerConfig;
import org.itransition.taskmanager.jpa.entity.NotificationFrequency;
import org.itransition.taskmanager.mapper.ConsumerDtoMapper;
import org.itransition.taskmanager.mapper.ConsumerJpaMapper;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.dao.ConsumerRepository;
import org.itransition.taskmanager.service.email.EmailDetails;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class ConsumerService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ConsumerJpaMapper consumerJpaMapper;
    private final ConsumerDtoMapper consumerDtoMapper;
    private final ConsumerConfigService consumerConfigService;
    private final ConsumerRepository consumerRepository;

    private static final String ENTITY_NAME = "consumer";
    private static final String CONSUMER_CONFIG_ENTITY_NAME = "consumer config";

    /**
     * Finds Consumer entity and joins email column from ConsumerConfig
     */
    public ConsumerDto findById(Long id) {
        log.info("Fetching '" + ENTITY_NAME + "' entity with id {}"
                + " from the JPA datastore unit", id);
        Consumer consumer = findByIdOrThrow(id);
        String emailById = consumerConfigService.findEmailById(id);
        return consumerDtoMapper.map(consumer, emailById);
    }

    /**
     * Saves into database with default config, moreover sends notification
     * to the consumer email address that the registration was successful
     */
    public ConsumerDto save(ConsumerDto consumerDto) {
        log.info("Saving entities '" + ENTITY_NAME + "' "
                + CONSUMER_CONFIG_ENTITY_NAME + "' with name '{}', surname '{}' and email '{}'"
                + " to the JPA datastore unit", consumerDto.getName(), consumerDto.getSurname(),
                consumerDto.getEmail());

        if (consumerConfigService.existsByEmail(consumerDto.getEmail())) {
            throw new DuplicateEmailException("'" + ENTITY_NAME + "' entry with such email"
                    + " already exists");
        }

        Consumer consumer = consumerJpaMapper.map(consumerDto);
        ConsumerConfig consumerConfig = new ConsumerConfig();

        //set missing properties by default
        consumerConfig.setNotifications(false);
        consumerConfig.setNotificationFrequency(NotificationFrequency.EVERY_MONTH);
        consumerConfig.setEmail(consumerDto.getEmail());

        //needed for saving cascaded entity
        consumer.setConsumerConfig(consumerConfig);
        //needed for getting primary key from cascaded entity
        consumerConfig.setConsumer(consumer);

        Consumer savedConsumer = consumerRepository.save(consumer);

        EmailDetails emailDetails = new EmailDetails()
                .withSubject("Task manager: thanks for registration on our resource")
                .withDestinationEmail(consumerDto.getEmail())
                .withTemplateLocation(FreeMarkerTemplatesLocation.SUCCESS_REGISTRATION)
                .withTemplateProperty("name", consumerDto.getName())
                .withTemplateProperty("surname", consumerDto.getSurname());

        SuccessRegistrationEvent event = new SuccessRegistrationEvent(this, emailDetails);
        applicationEventPublisher.publishEvent(event);

        consumerDto.setId(savedConsumer.getId());
        return consumerDto;
    }

    /**
     * Updates 'Consumer' entity and his email inside of ConsumerConfig entity
     */
    public ConsumerDto updateById(Long consumerId, ConsumerDto consumerDto) {
        log.info("Updating entity '" + ENTITY_NAME + "' with name '{}', surname '{}'"
                        + " and unique email '{}' to the JPA datastore unit", consumerDto.getName(),
                consumerDto.getSurname(), consumerDto.getEmail());

        Consumer consumerById = findByIdOrThrow(consumerId);
        consumerJpaMapper.mapWithoutId(consumerDto, consumerById);

        consumerRepository.save(consumerById);
        consumerConfigService.updateEmailById(consumerDto.getEmail(), consumerId);

        consumerDto.setId(consumerId);
        return consumerDto;
    }

    /**
     * Finds all Consumer entities and joined email columns from config
     */
    public List<ConsumerDto> find(Pageable pageable) {
        log.info("Fetching '" + ENTITY_NAME + "' entities with 'notification_email' from '"
                + CONSUMER_CONFIG_ENTITY_NAME + "' entity from  the JPA datastore unit");

        return consumerRepository.findAll(pageable).stream()
                .map(entry -> consumerDtoMapper.map(entry,
                        consumerConfigService.findEmailById(entry.getId())))
                .collect(Collectors.toList());
    }

    public boolean existsById(Long id) {
        log.info("Verifying that '" + ENTITY_NAME + "' with id {} exists"
                + " in the JPA datastore unit ", id);
        return consumerRepository.existsById(id);
    }

    /**
     * Deletes Consumer, and it's config entities by id(have shared PK)
     */
    public void deleteById(Long id) {
        log.info("Deleting '" + ENTITY_NAME
                + ", '" + CONSUMER_CONFIG_ENTITY_NAME
                + "' entities with shared PK {} from the JPA datastore unit", id);
        if (!consumerRepository.existsById(id)) {
            throw new ModelNotFoundException("there is no entity '" + ENTITY_NAME
                    + "' with id: " + id);
        }
        consumerRepository.deleteById(id);
    }

    private Consumer findByIdOrThrow(Long id) {
        return consumerRepository.findById(id).orElseThrow(
                () -> new ModelNotFoundException("there is no entity '" + ENTITY_NAME
                        + "' with id: " + id));
    }

    /**
     * Finds all consumers by some frequency, who have enabled notifications
     */
    public List<ConsumerDto> findAllConsumersByEnabledNotificationsAndByFrequency(NotificationFrequency notificationFrequency) {
        log.info("Fetching '" + ENTITY_NAME + "' entities who have enabled notifications in"
                + " joined '" + CONSUMER_CONFIG_ENTITY_NAME + "' by notification frequency {}"
                + " from the JPA datastore unit", notificationFrequency);

        return consumerRepository.findByEnabledNotificationsAndByFrequency(notificationFrequency).stream()
                .map(entry -> consumerDtoMapper.map(entry,
                        consumerConfigService.findEmailById(entry.getId())))
                .collect(Collectors.toList());
    }
}
