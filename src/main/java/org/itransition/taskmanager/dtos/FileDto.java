package org.itransition.taskmanager.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {

    private String fileName;
    private int size;
    private String downloadPath;
}
