package org.itransition.taskmanager.service.dto;

import org.itransition.taskmanager.exception.DuplicateEmailException;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.mapper.dto.ConsumerDtoMapper;
import org.itransition.taskmanager.mapper.dto.ConsumerDtoMapperImpl;
import org.itransition.taskmanager.mapper.jpa.ConsumerJpaMapper;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.dao.ConsumerRepository;
import org.itransition.taskmanager.mapper.jpa.ConsumerJpaMapperImpl;
import org.itransition.taskmanager.utils.DtoUtils;
import org.itransition.taskmanager.utils.JpaUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceTest {

    private static final Long CONSUMER_ID = 165L;
    private static final String CONSUMER_EMAIL = "test-consumer@gmail.com";

    @Mock
    private ConsumerRepository consumerRepository;

    @Spy
    private ConsumerJpaMapper consumerJpaMapper = new ConsumerJpaMapperImpl();

    @Spy
    private ConsumerDtoMapper consumerDtoMapper = new ConsumerDtoMapperImpl();

    @InjectMocks
    private ConsumerService consumerService;

    @Test
    void testFindById() {
        Consumer consumerFromRepo = JpaUtils.generateConsumer();

        when(consumerRepository.findById(CONSUMER_ID))
                .thenReturn(Optional.of(consumerFromRepo));

        ConsumerDto actualConsumerDto = consumerService.findById(CONSUMER_ID);
        ConsumerDto expectedConsumerDto = DtoUtils.mapToConsumerDto(consumerFromRepo);
        assertEquals(expectedConsumerDto, actualConsumerDto);

        verify(consumerRepository)
                .findById(CONSUMER_ID);
    }

    @Test
    void testFindByIdThrowsModelNotFoundException() {
        when(consumerRepository.findById(CONSUMER_ID)).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                () -> consumerService.findById(CONSUMER_ID));

        verify(consumerRepository)
                .findById(CONSUMER_ID);
    }

    @Test
    void testSave() {
        Consumer consumer = JpaUtils.generateConsumer();
        consumer.setEmail(CONSUMER_EMAIL);

        when(consumerRepository.existsByEmail(CONSUMER_EMAIL))
                .thenReturn(false);

        when(consumerRepository.save(consumer))
                .thenReturn(consumer);

        ConsumerDto consumerDto = DtoUtils.mapToConsumerDto(consumer);
        ConsumerDto actualConsumerDto = consumerService.save(consumerDto);
        assertNotNull(actualConsumerDto);

        verify(consumerRepository)
                .existsByEmail(CONSUMER_EMAIL);

        verify(consumerRepository)
                .save(consumer);
    }

    @Test
    void testSaveThrowsDuplicateEmailException() {
        Consumer consumer = JpaUtils.generateConsumer();
        consumer.setEmail(CONSUMER_EMAIL);

        when(consumerRepository.existsByEmail(CONSUMER_EMAIL)).thenReturn(true);

        ConsumerDto consumerDto = DtoUtils.mapToConsumerDto(consumer);
        assertThrows(DuplicateEmailException.class,
                () -> consumerService.save(consumerDto));

        verify(consumerRepository)
                .existsByEmail(CONSUMER_EMAIL);

        verifyNoMoreInteractions(consumerRepository);
    }

    @Test
    void testUpdateById() {
        Consumer consumer = JpaUtils.generateConsumer();
        consumer.setId(CONSUMER_ID);

        when(consumerRepository.findById(CONSUMER_ID))
                .thenReturn(Optional.of(consumer));

        when(consumerRepository.save(consumer)).thenReturn(consumer);

        ConsumerDto consumerDto = DtoUtils.mapToConsumerDto(consumer);
        ConsumerDto consumerDtoFromService = consumerService
                .updateById(CONSUMER_ID, consumerDto);

        assertNotNull(consumerDtoFromService);

        verify(consumerRepository)
                .findById(CONSUMER_ID);

        verify(consumerRepository)
                .save(consumer);
    }

    @Test
    void testUpdateByIdThrowsModelNotFoundException() {
        Consumer consumer = JpaUtils.generateConsumer();
        consumer.setId(CONSUMER_ID);

        when(consumerRepository.findById(CONSUMER_ID)).thenReturn(Optional.empty());

        ConsumerDto consumerDto = DtoUtils.mapToConsumerDto(consumer);
        assertThrows(ModelNotFoundException.class,
                () -> consumerService.updateById(CONSUMER_ID, consumerDto));

        verify(consumerRepository)
                .findById(CONSUMER_ID);

        verifyNoMoreInteractions(consumerRepository);
    }

    @Test
    void testFind() {
        Page<Consumer> consumerPage = new PageImpl<>(
                List.of(JpaUtils.generateConsumer(), JpaUtils.generateConsumer()));

        Pageable pageable = PageRequest.ofSize(2);
        when(consumerRepository.findAll(pageable)).thenReturn(consumerPage);

        List<ConsumerDto> consumerDtos = consumerService.find(pageable);
        assertEquals(2, consumerDtos.size());

        verify(consumerRepository)
                .findAll(pageable);
    }

    @Test
    void testDeleteById() {
        when(consumerRepository.existsById(CONSUMER_ID))
                .thenReturn(true);

        consumerService.deleteById(CONSUMER_ID);

        verify(consumerRepository)
                .existsById(CONSUMER_ID);

        verify(consumerRepository)
                .deleteById(CONSUMER_ID);
    }

    @Test
    void testDeleteByIdThrowsModelNotFoundException() {
        when(consumerRepository.existsById(CONSUMER_ID))
                .thenReturn(false);

        assertThrows(ModelNotFoundException.class,
                () -> consumerService.deleteById(CONSUMER_ID));

        verify(consumerRepository)
                .existsById(CONSUMER_ID);

        verifyNoMoreInteractions(consumerRepository);
    }
}
