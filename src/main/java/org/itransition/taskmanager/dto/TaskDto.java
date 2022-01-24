package org.itransition.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"title", "description", "creationDate",
        "expirationDate", "donePercentage", "priority", "status"}, callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto implements Dto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "'title' property must be not null")
    private String title;

    @NotNull(message = "'description' property must be not null")
    private String description;

    @NotNull(message = "'creationDate' property must be not null")
    private Date creationDate;

    @NotNull(message = "'expirationDate' property must be not null")
    private Date expirationDate;

    @Min(value = 0, message = "'donePercentage' property cannot be less that 0")
    @Max(value = 100, message = "'donePercentage' property cannot be more that 100")
    @NotNull(message = "'donePercentage' property must be not null")
    private Integer donePercentage;

    @NotNull(message = "'priority' property must be not null")
    private String priority;

    @NotNull(message = "'status' property must be not null")
    private String status;
}
