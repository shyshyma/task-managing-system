package org.itransition.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name", callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachedFileDto implements Dto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "'content' property cannot be null")
    private String content;

    @NotNull(message = "'name' property cannot be null")
    private String name;
}
