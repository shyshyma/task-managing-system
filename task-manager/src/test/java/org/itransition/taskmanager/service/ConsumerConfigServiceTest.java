package org.itransition.taskmanager.service;

import org.itransition.taskmanager.dto.ConsumerConfigDto;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.jpa.dao.ConsumerConfigRepository;
import org.itransition.taskmanager.jpa.entity.ConsumerConfig;
import org.itransition.taskmanager.mapper.ConsumerConfigDtoMapper;
import org.itransition.taskmanager.mapper.ConsumerConfigDtoMapperImpl;
import org.itransition.taskmanager.utils.DtoUtils;
import org.itransition.taskmanager.utils.JpaUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.any;


@ExtendWith(MockitoExtension.class)
class ConsumerConfigServiceTest {

    private static final Long CONSUMER_CONFIG_ID = 165L;
    private static final String CONSUMER_CONFIG_EMAIL = "test-email@gmail.com";

    @Spy
    private ConsumerConfigDtoMapper consumerConfigDtoMapper = new ConsumerConfigDtoMapperImpl();

    @Mock
    private ConsumerConfigRepository consumerConfigRepository;

    @InjectMocks
    private ConsumerConfigService consumerConfigService;

    @Test
    void testExistsById() {
        when(consumerConfigRepository.existsById(CONSUMER_CONFIG_ID)).thenReturn(true);

        assertTrue(consumerConfigService.existsById(CONSUMER_CONFIG_ID));
        verify(consumerConfigRepository).existsById(CONSUMER_CONFIG_ID);
    }

    @Test
    void testExistsByEmail() {
        when(consumerConfigRepository.existsByEmail(CONSUMER_CONFIG_EMAIL)).thenReturn(true);

        assertTrue(consumerConfigService.existsByEmail(CONSUMER_CONFIG_EMAIL));
        verify(consumerConfigRepository).existsByEmail(CONSUMER_CONFIG_EMAIL);
    }

    @Test
    void testFindById() {
        ConsumerConfig consumerConfig = JpaUtils.generateConsumerConfig();
        when(consumerConfigRepository.findById(CONSUMER_CONFIG_ID))
                .thenReturn(Optional.of(consumerConfig));

        ConsumerConfigDto byId = consumerConfigService.findById(CONSUMER_CONFIG_ID);
        assertNotNull(byId);

        verify(consumerConfigRepository).findById(CONSUMER_CONFIG_ID);
    }

    @Test
    void testFindByIdThrowsModelNotFoundException() {
        when(consumerConfigRepository.findById(CONSUMER_CONFIG_ID))
                .thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                () -> consumerConfigService.findById(CONSUMER_CONFIG_ID));

        verify(consumerConfigRepository).findById(CONSUMER_CONFIG_ID);
    }

    @Test
    void testFindEmailById() {
        when(consumerConfigRepository.existsById(CONSUMER_CONFIG_ID))
                .thenReturn(true);

        when(consumerConfigRepository.findEmailById(CONSUMER_CONFIG_ID))
                .thenReturn(CONSUMER_CONFIG_EMAIL);

        assertNotNull(consumerConfigService.findEmailById(CONSUMER_CONFIG_ID));

        verify(consumerConfigRepository).existsById(CONSUMER_CONFIG_ID);
        verify(consumerConfigRepository).findEmailById(CONSUMER_CONFIG_ID);
    }

    @Test
    void testFindEmailByIdThrowsModelNotFoundException() {
        when(consumerConfigRepository.existsById(CONSUMER_CONFIG_ID))
                .thenReturn(false);

        assertThrows(ModelNotFoundException.class,
                () -> consumerConfigService.findEmailById(CONSUMER_CONFIG_ID));

        verify(consumerConfigRepository).existsById(CONSUMER_CONFIG_ID);
        verifyNoMoreInteractions(consumerConfigRepository);
    }

    @Test
    void testUpdateEmailById() {
        when(consumerConfigRepository.existsById(CONSUMER_CONFIG_ID))
                .thenReturn(true);

        consumerConfigService.updateEmailById(CONSUMER_CONFIG_EMAIL, CONSUMER_CONFIG_ID);

        verify(consumerConfigRepository).existsById(CONSUMER_CONFIG_ID);
        verify(consumerConfigRepository).updateEmailById(CONSUMER_CONFIG_EMAIL, CONSUMER_CONFIG_ID);
    }

    @Test
    void testUpdateEmailByIdThrowsModelNotFoundException() {
        when(consumerConfigRepository.existsById(CONSUMER_CONFIG_ID))
                .thenReturn(false);

        assertThrows(ModelNotFoundException.class,
                () -> consumerConfigService.updateEmailById(CONSUMER_CONFIG_EMAIL, CONSUMER_CONFIG_ID));

        verify(consumerConfigRepository).existsById(CONSUMER_CONFIG_ID);
        verifyNoMoreInteractions(consumerConfigRepository);
    }

    @Test
    void testUpdateById() {
        when(consumerConfigRepository.findById(CONSUMER_CONFIG_ID))
                .thenReturn(Optional.of(JpaUtils.generateConsumerConfig()));

        ConsumerConfigDto configDto = DtoUtils.generateConsumerConfigDto();
        ConsumerConfig consumerConfig = JpaUtils.mapToConsumerConfig(configDto);
        when(consumerConfigRepository.save(any(ConsumerConfig.class))).thenReturn(consumerConfig);

        assertNotNull(consumerConfigService.updateById(CONSUMER_CONFIG_ID, configDto));

        verify(consumerConfigRepository).findById(CONSUMER_CONFIG_ID);
        verify(consumerConfigRepository).save(any(ConsumerConfig.class));

    }

    @Test
    void testUpdateByIdThrowsModelNotFoundException() {
        when(consumerConfigRepository.findById(CONSUMER_CONFIG_ID))
                .thenReturn(Optional.empty());

        ConsumerConfigDto configDto = DtoUtils.generateConsumerConfigDto();
        assertThrows(ModelNotFoundException.class,
                () -> consumerConfigService.updateById(CONSUMER_CONFIG_ID, configDto));

        verify(consumerConfigRepository).findById(CONSUMER_CONFIG_ID);
        verifyNoMoreInteractions(consumerConfigRepository);
    }
}
