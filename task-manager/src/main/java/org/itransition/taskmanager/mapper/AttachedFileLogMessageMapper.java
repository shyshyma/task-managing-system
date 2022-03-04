package org.itransition.taskmanager.mapper;

import org.itransition.taskmanager.mb.AttachedFileLogMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AttachedFileLogMessageMapper {

    public abstract AttachedFileLogMessage map(String fileName, String message);
}
