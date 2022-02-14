package org.itransition.taskmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.dto.ConsumerConfigDto;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.jpa.dao.ConsumerConfigRepository;
import org.itransition.taskmanager.jpa.entity.ConsumerConfig;
import org.itransition.taskmanager.mapper.ConsumerConfigDtoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
@CacheConfig(cacheNames = "consumer-config")
public class ConsumerConfigService {

    private final ConsumerConfigDtoMapper consumerConfigDtoMapper;
    private final ConsumerConfigRepository consumerConfigRepository;

    private static final String ENTITY_NAME = "consumer config";

    @Cacheable(cacheNames = "consumer-config-exists-by-id")
    public boolean existsById(Long id) {
        log.info("Verifying that '" + ENTITY_NAME + "' with id {} exists"
                + " in the JPA datastore unit ", id);
        return consumerConfigRepository.existsById(id);
    }

    @Cacheable(cacheNames = "consumer-config-exists-by-email")
    public boolean existsByEmail(String email) {
        log.info("Verifying that '" + ENTITY_NAME + "' with unique email '{}' exists"
                + " in the JPA datastore unit ", email);
        return consumerConfigRepository.existsByEmail(email);
    }

    @Cacheable
    public ConsumerConfigDto findById(Long id) {
        log.info("Fetching '" + ENTITY_NAME + "' entity with id {} from the JPA datastore unit", id);
        ConsumerConfig consumerConfigById = findByIdOrThrow(id);
        return consumerConfigDtoMapper.map(consumerConfigById);
    }

    @Cacheable(cacheNames = "consumer-config-email")
    public String findEmailById(Long id) {
        log.info("Fetching 'notification_email' from '" + ENTITY_NAME + "' by id '{}'", id);
        if (!consumerConfigRepository.existsById(id)) {
            throw new ModelNotFoundException("No '" + ENTITY_NAME + "' entity exists by PK " + id);
        }
        return consumerConfigRepository.findEmailById(id);
    }

    @CachePut(key = "#id", cacheNames = "consumer-config-email")
    public String updateEmailById(String email, Long id) {
        log.info("Updating 'notification_email' by value {} for '" + ENTITY_NAME + "' entity by id"
                + " '{}'", email, id);
        if (!consumerConfigRepository.existsById(id)) {
            throw new ModelNotFoundException("No '" + ENTITY_NAME + "' entity exists by PK " + id);
        }
        consumerConfigRepository.updateEmailById(email, id);
        return email;
    }

    @CachePut(key = "#consumerConfigId")
    public ConsumerConfigDto updateById(Long consumerConfigId, ConsumerConfigDto consumerConfigDto) {
        log.info("Updating entity '" + ENTITY_NAME + "' by id {} to the JPA datastore unit", consumerConfigId);

        ConsumerConfig configById = findByIdOrThrow(consumerConfigId);
        BeanUtils.copyProperties(consumerConfigDto, configById, "id");
        consumerConfigRepository.save(configById);

        consumerConfigDto.setId(consumerConfigId);
        return consumerConfigDto;
    }

    @CacheEvict(cacheNames = {
            "consumer-config",
            "consumer-config-exists-by-id",
            "consumer-config-exists-by-email",
            "consumer-config-email"})
    public void deleteById(Long id) {
        log.info("Deleting '" + ENTITY_NAME + "' by id {}", id);
        if (!consumerConfigRepository.existsById(id)) {
            throw new ModelNotFoundException("No '" + ENTITY_NAME + "' entity exists by PK " + id);
        }
        consumerConfigRepository.deleteById(id);
    }

    private ConsumerConfig findByIdOrThrow(Long id) {
        return consumerConfigRepository.findById(id).orElseThrow(
                () -> new ModelNotFoundException("there is no entity '" + ENTITY_NAME
                        + "' with id: " + id));
    }
}
