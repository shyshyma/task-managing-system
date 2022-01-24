package org.itransition.taskmanager.service.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.exception.DuplicateEmailException;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.mapper.dto.ConsumerDtoMapper;
import org.itransition.taskmanager.mapper.jpa.ConsumerJpaMapper;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.dao.ConsumerRepository;
import org.springframework.beans.BeanUtils;
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

    private static final String ENTITY_NAME = "consumer";

    private final ConsumerJpaMapper consumerJpaMapper;
    private final ConsumerDtoMapper consumerDtoMapper;

    private final ConsumerRepository consumerRepository;

    public ConsumerDto findById(Long id) {
        log.info("Fetching '" + ENTITY_NAME + "' entity with id {}" +
                " from the JPA datastore unit", id);

        Consumer consumer = findByIdOrExceptionThrown(id);

        return consumerDtoMapper.map(consumer);
    }

    public ConsumerDto save(ConsumerDto consumerDto) {
        log.info("Saving entity '" + ENTITY_NAME + "' with name '{}', surname '{}' and unique email" +
                        " '{}' to the JPA datastore unit", consumerDto.getName(),
                consumerDto.getSurname(), consumerDto.getEmail());

        Consumer consumer = consumerJpaMapper.map(consumerDto);
        if (!consumerRepository.existsByEmail(consumer.getEmail())) {
            Consumer savedConsumer = consumerRepository.save(consumer);

            return consumerDtoMapper.map(savedConsumer);
        }

        throw new DuplicateEmailException("'" + ENTITY_NAME + "' entry with such email" +
                " already exists");
    }

    public ConsumerDto updateById(Long consumerId, ConsumerDto consumerDto) {
        log.info("Updating entity '" + ENTITY_NAME + "' with name '{}', surname '{}'" +
                        " and unique email '{}' to the JPA datastore unit", consumerDto.getName(),
                consumerDto.getSurname(), consumerDto.getEmail());

        Consumer consumerById = findByIdOrExceptionThrown(consumerId);

        BeanUtils.copyProperties(consumerDto, consumerById, "id");
        Consumer savedConsumer = consumerRepository.save(consumerById);

        return consumerDtoMapper.map(savedConsumer);
    }

    public List<ConsumerDto> find(Pageable pageable) {
        log.info("Fetching '" + ENTITY_NAME + "' entities from the JPA datastore unit");

        return consumerRepository.findAll(pageable).stream()
                .map(consumerDtoMapper::map)
                .collect(Collectors.toList());
    }

    public boolean existsById(Long id) {
        log.info("Verifying that '" + ENTITY_NAME + "' with id {} exists" +
                " in the JPA datastore unit ", id);

        return consumerRepository.existsById(id);
    }

    public void deleteById(Long id) {
        log.info("Deleting '" + ENTITY_NAME + "' entity with id {} from the JPA datastore unit", id);

        if (!consumerRepository.existsById(id)) {
            throw new ModelNotFoundException("there is no entity '" + ENTITY_NAME +
                    "' with id: " + id);
        }

        consumerRepository.deleteById(id);
    }

    private Consumer findByIdOrExceptionThrown(Long id) {
       return consumerRepository.findById(id).orElseThrow(
               () -> new ModelNotFoundException("there is no entity '" + ENTITY_NAME +
                        "' with id: " + id));
    }
}