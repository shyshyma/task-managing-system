package org.itransition.audit.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerLogMessage extends AbstractLogMessage {

    private String firstName;
    private String lastName;
}
