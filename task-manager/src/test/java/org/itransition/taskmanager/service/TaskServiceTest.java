package org.itransition.taskmanager.service;

import org.itransition.taskmanager.exception.DuplicateTitleException;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.mapper.TaskDtoMapper;
import org.itransition.taskmanager.mapper.TaskDtoMapperImpl;
import org.itransition.taskmanager.mapper.ConsumerJpaMapper;
import org.itransition.taskmanager.mapper.ConsumerJpaMapperImpl;
import org.itransition.taskmanager.mapper.TaskJpaMapper;
import org.itransition.taskmanager.mapper.TaskLogMessageMapper;
import org.itransition.taskmanager.mapper.TaskLogMessageMapperImpl;
import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.dto.TaskDto;
import org.itransition.taskmanager.jpa.entity.Task;
import org.itransition.taskmanager.jpa.dao.TaskRepository;
import org.itransition.taskmanager.mapper.TaskJpaMapperImpl;
import org.itransition.taskmanager.mb.Producer;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    private static final Long TASK_ID = 345L;
    private static final Long CONSUMER_ID = 567L;
    private static final String TASK_TITLE = "test task title";

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ConsumerService consumerService;

    @Mock
    private Producer producer;

    @Spy
    private TaskDtoMapper taskDtoMapper = new TaskDtoMapperImpl();

    @Spy
    private TaskJpaMapper taskJpaMapper = new TaskJpaMapperImpl();

    @Spy
    private ConsumerJpaMapper consumerJpaMapper = new ConsumerJpaMapperImpl();

    @Spy
    private TaskLogMessageMapper taskLogMessageMapper = new TaskLogMessageMapperImpl();

    @InjectMocks
    private TaskService taskService;

    @Test
    void testSaveToConsumer() {
        when(taskRepository.existsByTitle(TASK_TITLE))
                .thenReturn(false);

        when(consumerService.existsById(CONSUMER_ID))
                .thenReturn(true);

        ConsumerDto consumerDto = DtoUtils.generateConsumerDto();
        when(consumerService.findById(CONSUMER_ID)).thenReturn(consumerDto);

        Task task = JpaUtils.generateTask();
        task.setTitle(TASK_TITLE);

        when(taskRepository.save(task)).thenReturn(task);

        TaskDto taskDto = DtoUtils.mapToTaskDto(task);
        TaskDto taskDtoFromService = taskService.saveToConsumer(taskDto, CONSUMER_ID);
        assertNotNull(taskDtoFromService);

        verify(taskRepository).existsByTitle(anyString());

        verify(consumerService).existsById(anyLong());

        verify(consumerService).findById(anyLong());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testSaveToConsumerThrowsDuplicateTitleException() {
        when(taskRepository.existsByTitle(TASK_TITLE)).thenReturn(true);

        TaskDto taskDto = DtoUtils.generateTaskDto();
        taskDto.setTitle(TASK_TITLE);

        assertThrows(DuplicateTitleException.class,
                () -> taskService.saveToConsumer(taskDto, CONSUMER_ID));

        verify(taskRepository).existsByTitle(TASK_TITLE);

        verifyNoMoreInteractions(consumerService);

        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testSaveToConsumerThrowsModelNotFoundException() {
        when(taskRepository.existsByTitle(TASK_TITLE)).thenReturn(false);
        when(consumerService.existsById(CONSUMER_ID)).thenReturn(false);

        TaskDto taskDto = DtoUtils.generateTaskDto();
        taskDto.setTitle(TASK_TITLE);

        assertThrows(ModelNotFoundException.class,
                () -> taskService.saveToConsumer(taskDto, CONSUMER_ID));

        verify(taskRepository).existsByTitle(TASK_TITLE);

        verify(consumerService).existsById(CONSUMER_ID);

        verifyNoMoreInteractions(consumerService);

        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testUpdateByIdAndConsumerId() {
        Task task = JpaUtils.generateTask();

        when(taskRepository.findByIdAndConsumerId(TASK_ID, CONSUMER_ID))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(task)).thenReturn(task);

        TaskDto taskDto = DtoUtils.mapToTaskDto(task);
        TaskDto taskDtoFromService = taskService
                .updateByIdAndConsumerId(TASK_ID, CONSUMER_ID, taskDto);

        assertNotNull(taskDtoFromService);

        verify(taskRepository).findByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verify(taskRepository).save(task);
    }

    @Test
    void testUpdateByIdAndConsumerIdThrowsModelNotFoundException() {
        when(taskRepository.findByIdAndConsumerId(TASK_ID, CONSUMER_ID))
                .thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                () -> taskService.findByIdAndConsumerId(TASK_ID, CONSUMER_ID));

        verify(taskRepository)
                .findByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testFindByIdAndConsumerId() {
        Task task = JpaUtils.generateTask();
        task.setTitle(TASK_TITLE);

        when(taskRepository.findByIdAndConsumerId(TASK_ID, CONSUMER_ID))
                .thenReturn(Optional.of(task));

        TaskDto taskDtoFromService = taskService
                .findByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        assertNotNull(taskDtoFromService);

        verify(taskRepository)
                .findByIdAndConsumerId(TASK_ID, CONSUMER_ID);
    }

    @Test
    void testFindByIdAndConsumerIdThrowsModelNotFoundException() {
        when(taskRepository.findByIdAndConsumerId(TASK_ID, CONSUMER_ID))
                .thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                () -> taskService.findByIdAndConsumerId(TASK_ID, CONSUMER_ID));

        verify(taskRepository)
                .findByIdAndConsumerId(TASK_ID, CONSUMER_ID);
    }

    @Test
    void testFindByConsumerId() {
        Page<Task> taskPage = new PageImpl<>(List.of(JpaUtils.generateTask(),
                JpaUtils.generateTask()));

        Pageable pageable = PageRequest.ofSize(2);
        when(taskRepository.findByConsumerId(CONSUMER_ID, pageable))
                .thenReturn(taskPage);

        List<TaskDto> taskDtos = taskService.findByConsumerId(CONSUMER_ID, pageable);
        assertEquals(2, taskDtos.size());

        verify(taskRepository)
                .findByConsumerId(CONSUMER_ID, pageable);
    }

    @Test
    void testDeleteByIdAndConsumerId() {
        when(taskRepository.existsByIdAndConsumerId(TASK_ID, CONSUMER_ID))
                .thenReturn(true);

        taskService.deleteByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verify(taskRepository)
                .existsByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verify(taskRepository)
                .deleteByIdAndConsumerId(TASK_ID, CONSUMER_ID);
    }

    @Test
    void testDeleteByIdAndConsumerIdThrowsModelNotFoundException() {
        when(taskRepository.existsByIdAndConsumerId(TASK_ID, CONSUMER_ID))
                .thenReturn(false);

        assertThrows(ModelNotFoundException.class,
                () -> taskService.deleteByIdAndConsumerId(TASK_ID, CONSUMER_ID));

        verify(taskRepository)
                .existsByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verifyNoMoreInteractions(taskRepository);
    }
}
