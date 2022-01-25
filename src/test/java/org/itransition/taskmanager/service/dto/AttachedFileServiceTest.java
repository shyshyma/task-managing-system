package org.itransition.taskmanager.service.dto;

import org.itransition.taskmanager.dto.FileMetadataDto;
import org.itransition.taskmanager.exception.ModelNotFoundException;
import org.itransition.taskmanager.mapper.dto.AttachedFileDtoMapper;
import org.itransition.taskmanager.mapper.dto.FileMetadataDtoMapper;
import org.itransition.taskmanager.mapper.jpa.AttachedFileJpaMapper;
import org.itransition.taskmanager.mapper.jpa.TaskJpaMapper;
import org.itransition.taskmanager.dto.AttachedFileDto;
import org.itransition.taskmanager.jpa.entity.AttachedFile;
import org.itransition.taskmanager.jpa.dao.AttachedFileRepository;
import org.itransition.taskmanager.utils.DtoUtils;
import org.itransition.taskmanager.utils.JpaUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AttachedFileServiceTest {

    private static final Long TASK_ID = 16L;
    private static final Long CONSUMER_ID = 16L;
    private static final String ATTACHED_FILE_NAME = "test-file-name.txt";

    @Mock
    private TaskService taskDtoService;

    @Mock
    private AttachedFileRepository attachedFileRepository;

    @Spy
    private AttachedFileDtoMapper attachedFileDtoMapper = Mappers.getMapper(AttachedFileDtoMapper.class);

    @Spy
    private TaskJpaMapper taskJpaMapper = Mappers.getMapper(TaskJpaMapper.class);

    @Spy
    private FileMetadataDtoMapper fileMetadataDtoMapper = Mappers.getMapper(FileMetadataDtoMapper.class);

    @Spy
    private AttachedFileJpaMapper attachedFileJpaMapper = Mappers.getMapper(AttachedFileJpaMapper.class);

    @InjectMocks
    private AttachedFileService attachedFileService;

    @Test
    void testSaveToTaskWithConsumer() {
        when(taskDtoService.existsByIdAndConsumerId(TASK_ID, CONSUMER_ID)).thenReturn(true);

        AttachedFileDto attachedFileDto = DtoUtils.generateAttachedFileDto();

        FileMetadataDto fileMetadataDto = attachedFileService
                .saveToTaskWithConsumer(attachedFileDto, TASK_ID, CONSUMER_ID);

        assertNotNull(fileMetadataDto);

        verify(taskDtoService, times(1))
                .existsByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verify(taskDtoService, times(1))
                .findByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verify(attachedFileRepository, times(1))
                .save(any(AttachedFile.class));
    }

    @Test
    void testSaveToTaskWithConsumerThrowsModelNotFoundException() {
        when(taskDtoService.existsByIdAndConsumerId(TASK_ID, CONSUMER_ID)).thenReturn(false);

        AttachedFileDto attachedFileDto = DtoUtils.generateAttachedFileDto();

        assertThrows(ModelNotFoundException.class,
                () -> attachedFileService
                        .saveToTaskWithConsumer(attachedFileDto, TASK_ID, CONSUMER_ID));

        verify(taskDtoService, times(1))
                .existsByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verify(taskDtoService, times(0))
                .findByIdAndConsumerId(TASK_ID, CONSUMER_ID);

        verify(attachedFileRepository, times(0))
                .save(any(AttachedFile.class));
    }

    @Test
    void testUpdateToTaskWithConsumer() {
        AttachedFileDto attachedFileDto = DtoUtils.generateAttachedFileDto();
        attachedFileDto.setName(ATTACHED_FILE_NAME);

        when(attachedFileRepository
                .findByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID))
                .thenReturn(Optional.of(JpaUtils.mapToAttachedFile(attachedFileDto)));

        FileMetadataDto fileMetadataDto =
                attachedFileService.updateToTaskWithConsumer(attachedFileDto,
                        TASK_ID, CONSUMER_ID);

        assertNotNull(fileMetadataDto);

        verify(attachedFileRepository, times(1))
                .findByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);

        verify(attachedFileRepository, times(1))
                .save(any(AttachedFile.class));
    }

    @Test
    void testUpdateToTaskWithConsumerThrowsModelNotFoundException() {
        when(attachedFileRepository
                .findByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID))
                .thenReturn(Optional.empty());

        AttachedFileDto attachedFileDto = DtoUtils.generateAttachedFileDto();
        attachedFileDto.setName(ATTACHED_FILE_NAME);

        assertThrows(ModelNotFoundException.class,
                () -> attachedFileService
                        .updateToTaskWithConsumer(attachedFileDto, TASK_ID, CONSUMER_ID));

        verify(attachedFileRepository, times(1))
                .findByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);

        verify(attachedFileRepository, times(0))
                .save(any(AttachedFile.class));
    }

    @Test
    void testFindByTaskIdAndConsumerId() {

        Page<AttachedFile> attachedFilesPage = new PageImpl<>(List.of(JpaUtils.generateAttachedFile(),
                JpaUtils.generateAttachedFile()));

        Pageable pageable = PageRequest.ofSize(2);

        when(attachedFileRepository
                .findByTaskIdAndTaskConsumerId(TASK_ID, CONSUMER_ID, pageable))
                .thenReturn(attachedFilesPage);

        List<AttachedFileDto> attachedFileDtos = attachedFileService
                .findByTaskIdAndConsumerId(TASK_ID, CONSUMER_ID, pageable);

        assertEquals(2, attachedFileDtos.size());

        verify(attachedFileRepository, times(1))
                .findByTaskIdAndTaskConsumerId(TASK_ID, CONSUMER_ID, pageable);
    }

    @Test
    void testFindByNameAndTaskIdAndConsumerId() {
        AttachedFile attachedFile = JpaUtils.generateAttachedFile();

        when(attachedFileRepository
                .findByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID))
                .thenReturn(Optional.of(attachedFile));

        AttachedFileDto expectedTaskDto = DtoUtils.mapToFileDto(attachedFile);

        AttachedFileDto actualTaskDto = attachedFileService
                .findByNameAndTaskIdAndConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);

        assertEquals(expectedTaskDto, actualTaskDto);

        verify(attachedFileRepository, times(1))
                .findByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);
    }

    @Test
    void testFindByNameAndTaskIdAndConsumerIdThrowsModelNotFoundException() {
        when(attachedFileRepository
                .findByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID))
                .thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                () -> attachedFileService.findByNameAndTaskIdAndConsumerId(
                        ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID));

        verify(attachedFileRepository, times(1))
                .findByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);
    }

    @Test
    void testDeleteByNameAndTaskIdAndConsumerId() {
        when(attachedFileRepository
                .existsByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID))
                .thenReturn(true);

        attachedFileService.deleteByNameAndTaskIdAndConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);

        verify(attachedFileRepository, times(1))
                .existsByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);

        verify(attachedFileRepository, times(1))
                .deleteByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);
    }

    @Test
    void testDeleteByNameAndTaskIdAndConsumerIdThrowsModelNotFoundException() {
        when(attachedFileRepository
                .existsByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID))
                .thenReturn(false);

        assertThrows(ModelNotFoundException.class,
                () -> attachedFileService
                        .deleteByNameAndTaskIdAndConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID));

        verify(attachedFileRepository, times(1))
                .existsByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);

        verify(attachedFileRepository, times(0))
                .deleteByNameAndTaskIdAndTaskConsumerId(ATTACHED_FILE_NAME, TASK_ID, CONSUMER_ID);
    }
}
