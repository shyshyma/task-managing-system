package org.itransition.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "expiredTaskDtoList")
public class ExpiredTasksEmailDto implements Dto {

    private String name;
    private String surname;
    private String email;
    private List<ExpiredTaskDto> expiredTaskDtoList = new ArrayList<>();
}
