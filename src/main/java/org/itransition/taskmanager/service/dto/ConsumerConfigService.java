package org.itransition.taskmanager.service.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.dto.ConsumerConfigDto;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.jpa.dao.ConsumerConfigRepository;
import org.itransition.taskmanager.jpa.entity.ConsumerConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class ConsumerConfigService {

    private final ConsumerConfigRepository consumerConfigRepository;

    private static final String ENTITY_NAME = "consumer config";

    public boolean existsById(Long id) {
        log.info("Verifying that '" + ENTITY_NAME + "' with id {} exists"
                + " in the JPA datastore unit ", id);
        return consumerConfigRepository.existsById(id);
    }

    public boolean existsByEmail(String email) {
        log.info("Verifying that '" + ENTITY_NAME + "' with unique email '{}' exists"
                + " in the JPA datastore unit ", email);
        return consumerConfigRepository.existsByEmail(email);
    }

    public String findEmailById(Long id) {
        log.info("Fetching 'notification_email' from '" + ENTITY_NAME + "' by id '{}'", id);
        if (!consumerConfigRepository.existsById(id)) {
            throw new ModelNotFoundException("No '" + ENTITY_NAME + "' entity exists by PK " + id);
        }
        return consumerConfigRepository.findEmailById(id);
    }

    public void updateEmailById(String email, Long id) {
        log.info("Updating 'notification_email' by value {} for '" + ENTITY_NAME + "' entity by id"
                + " '{}'", email, id);
        consumerConfigRepository.updateEmailById(email, id);
    }

    public ConsumerConfigDto update(Long consumerConfigId, ConsumerConfigDto consumerConfigDto) {
        log.info("Updating entity '" + ENTITY_NAME + "' by id {} to the JPA datastore unit", consumerConfigId);

        ConsumerConfig configById = consumerConfigRepository.findById(consumerConfigId).orElseThrow(
                () -> new ModelNotFoundException("there is no entity '" + ENTITY_NAME
                        + "' with id: " + consumerConfigId));
        BeanUtils.copyProperties(consumerConfigDto, configById, "id");
        consumerConfigRepository.save(configById);

        consumerConfigDto.setId(consumerConfigId);
        return consumerConfigDto;
    }

    public void deleteById(Long id) {
        log.info("Deleting '" + ENTITY_NAME + "' by id {}", id);
        consumerConfigRepository.deleteById(id);
    }
}
