package org.itransition.taskmanager.services.jpa;

import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.exceptions.DuplicateEmailException;
import org.itransition.taskmanager.models.jpa.Consumer;
import org.itransition.taskmanager.repositories.jpa.ConsumerRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService extends BaseCrudService<Consumer, ConsumerRepository> {

    private static final String ENTITY_NAME = "consumer";

    @Override
    public Consumer save(Consumer consumer) {
        log.info("Saving entity '" + ENTITY_NAME + "' with name '{}', surname '{}' and unique email" +
                        " '{}' to the JPA datastore unit", consumer.getFirstName(),
                consumer.getSecondName(), consumer.getEmail());

        if (!repository.existsByEmail(consumer.getEmail())) {
            return repository.save(consumer);
        }

        throw new DuplicateEmailException("'" + ENTITY_NAME + "' entry with such email" +
                " already exists");
    }
}
