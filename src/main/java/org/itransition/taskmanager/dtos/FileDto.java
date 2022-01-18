package org.itransition.taskmanager.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileDto {

    private String fileName;
    private int size;
    private String downloadPath;
}
