package org.itransition.taskmanager.mb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskLogMessage extends AbstractLogMessage {

    private String taskTitle;
}
