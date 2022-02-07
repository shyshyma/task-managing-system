package org.itransition.taskmanager.service;

import org.itransition.taskmanager.exception.DuplicateEmailException;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.mapper.ConsumerDtoMapper;
import org.itransition.taskmanager.mapper.ConsumerDtoMapperImpl;
import org.itransition.taskmanager.mapper.ConsumerJpaMapper;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.jpa.entity.Consumer;
import org.itransition.taskmanager.jpa.dao.ConsumerRepository;
import org.itransition.taskmanager.mapper.ConsumerJpaMapperImpl;
import org.itransition.taskmanager.utils.DtoUtils;
import org.itransition.taskmanager.utils.JpaUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.lenient;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceTest {

    private static final Long CONSUMER_ID = 165L;
    private static final String CONSUMER_EMAIL = "test-consumer@gmail.com";

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private ConsumerRepository consumerRepository;

    @Mock
    private ConsumerConfigService consumerConfigService;

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

        when(consumerConfigService.findEmailById(CONSUMER_ID))
                .thenReturn(CONSUMER_EMAIL);

        ConsumerDto actualConsumerDto = consumerService.findById(CONSUMER_ID);
        ConsumerDto expectedConsumerDto = DtoUtils.mapToConsumerDto(consumerFromRepo, CONSUMER_EMAIL);
        assertEquals(expectedConsumerDto, actualConsumerDto);

        verify(consumerRepository).findById(CONSUMER_ID);

        verify(consumerConfigService).findEmailById(CONSUMER_ID);
    }

    @Test
    void testFindByIdThrowsModelNotFoundException() {
        when(consumerRepository.findById(CONSUMER_ID)).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                () -> consumerService.findById(CONSUMER_ID));

        verify(consumerRepository)
                .findById(CONSUMER_ID);

        verifyNoMoreInteractions(consumerConfigService);
    }

    @Test
    void testSave() {
        Consumer consumer = JpaUtils.generateConsumer();
        when(consumerConfigService.existsByEmail(CONSUMER_EMAIL))
                .thenReturn(false);

        when(consumerRepository.save(any(Consumer.class)))
                .thenReturn(consumer);

        ConsumerDto consumerDto = DtoUtils.mapToConsumerDto(consumer, CONSUMER_EMAIL);
        ConsumerDto actualConsumerDto = consumerService.save(consumerDto);

        assertNotNull(actualConsumerDto);

        verify(consumerConfigService).existsByEmail(CONSUMER_EMAIL);

        verify(consumerRepository).save(any(Consumer.class));
    }

    @Test
    void testSaveThrowsDuplicateEmailException() {
        Consumer consumer = JpaUtils.generateConsumer();
        when(consumerConfigService.existsByEmail(CONSUMER_EMAIL)).thenReturn(true);

        ConsumerDto consumerDto = DtoUtils.mapToConsumerDto(consumer, CONSUMER_EMAIL);
        assertThrows(DuplicateEmailException.class,
                () -> consumerService.save(consumerDto));

        verify(consumerConfigService).existsByEmail(CONSUMER_EMAIL);

        verifyNoMoreInteractions(consumerRepository);
    }

    @Test
    void testUpdateById() {
        Consumer consumer = JpaUtils.generateConsumer();
        consumer.setId(CONSUMER_ID);

        when(consumerRepository.findById(CONSUMER_ID))
                .thenReturn(Optional.of(consumer));

        when(consumerRepository.save(consumer)).thenReturn(consumer);

        ConsumerDto consumerDto = DtoUtils.mapToConsumerDto(consumer, CONSUMER_EMAIL);
        ConsumerDto consumerDtoFromService = consumerService
                .updateById(CONSUMER_ID, consumerDto);

        assertNotNull(consumerDtoFromService);

        verify(consumerRepository).findById(CONSUMER_ID);

        verify(consumerRepository).save(consumer);

        verify(consumerConfigService).updateEmailById(CONSUMER_EMAIL, CONSUMER_ID);
    }

    @Test
    void testUpdateByIdThrowsModelNotFoundException() {
        Consumer consumer = JpaUtils.generateConsumer();
        consumer.setId(CONSUMER_ID);

        when(consumerRepository.findById(CONSUMER_ID)).thenReturn(Optional.empty());

        ConsumerDto consumerDto = DtoUtils.mapToConsumerDto(consumer, CONSUMER_EMAIL);
        assertThrows(ModelNotFoundException.class,
                () -> consumerService.updateById(CONSUMER_ID, consumerDto));

        verify(consumerRepository).findById(CONSUMER_ID);

        verifyNoMoreInteractions(consumerRepository);

        verifyNoMoreInteractions(consumerConfigService);
    }

    @Test
    void testFind() {
        List<Consumer> consumers = List.of(JpaUtils.generateConsumer(), JpaUtils.generateConsumer());

        Long firstConsumerId = consumers.get(0).getId();
        Long secondConsumerId = consumers.get(1).getId();

        lenient().when(consumerConfigService.findEmailById(firstConsumerId)).thenReturn("test@gmail.com");
        lenient().when(consumerConfigService.findEmailById(secondConsumerId)).thenReturn("test2@gmail.com");

        Page<Consumer> consumerPage = new PageImpl<>(
                List.of(JpaUtils.generateConsumer(), JpaUtils.generateConsumer()));

        Pageable pageable = PageRequest.ofSize(2);
        when(consumerRepository.findAll(pageable)).thenReturn(consumerPage);

        List<ConsumerDto> consumerDtos = consumerService.find(pageable);
        assertEquals(2, consumerDtos.size());

        verify(consumerRepository).findAll(pageable);

        verify(consumerConfigService, times(2)).findEmailById(anyLong());
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

        verify(consumerConfigService)
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

        verifyNoMoreInteractions(consumerConfigService);
    }
}
