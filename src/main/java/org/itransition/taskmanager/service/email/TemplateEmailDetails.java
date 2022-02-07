package org.itransition.taskmanager.service.email;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * For generating HTML email body from template engine
 *
 * @see EmailDetails
 */
@Getter
public class TemplateEmailDetails extends EmailDetails {

    private String templateLocation;
    private final Map<String, Object> templateProperties = new HashMap<>();

    public TemplateEmailDetails withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public TemplateEmailDetails withDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
        return this;
    }

    public TemplateEmailDetails withTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
        return this;
    }

    public TemplateEmailDetails withTemplateProperty(String key, Object value) {
        templateProperties.put(key, value);
        return this;
    }
}
