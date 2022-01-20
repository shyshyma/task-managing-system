package org.itransition.taskmanager.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileMetadataDto {

    private String fileName;
    private int size;
    private String downloadPath;
}
