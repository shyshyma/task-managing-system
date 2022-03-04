package org.itransition.audit.mq;

import lombok.Getter;
import lombok.Setter;
import org.itransition.common.mb.Message;

/**
 * For all logging messages that come to 'audit' microservice
 */
@Getter
@Setter
public abstract class AbstractLogMessage implements Message {

    private String message;
}
