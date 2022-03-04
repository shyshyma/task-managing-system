package org.itransition.audit.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachedFileLogMessage extends AbstractLogMessage {

    private String fileName;
}
