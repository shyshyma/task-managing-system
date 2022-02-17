package org.itransition.taskmanager.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class FileMetadataDto {

    private String fileName;
    private int size;
    private String downloadPath;
}
