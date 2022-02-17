package org.itransition.taskmanager.mapper;

import org.itransition.taskmanager.dto.ConsumerDto;
import org.itransition.taskmanager.service.email.EmailDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class EmailDetailsMapper {

    @Mapping(target = "withSubject", source = "subject")
    @Mapping(target = "withTemplateLocation", source = "templateLocation")
    @Mapping(target = "withDestinationEmail", source = "consumerDto.email")
    public abstract EmailDetails map(ConsumerDto consumerDto, String subject, String templateLocation);

    @AfterMapping
    public void setTemplateProperties(ConsumerDto consumerDto, @MappingTarget EmailDetails emailDetails) {
        emailDetails.withTemplateProperty("name", consumerDto.getName());
        emailDetails.withTemplateProperty("surname", consumerDto.getSurname());
    }
}
