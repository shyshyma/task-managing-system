package org.itransition.taskmanager.service.email;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides core sending email letter information
 * and core info for generating html text from template engine
 */
@Getter
@NoArgsConstructor
public class EmailDetails {

    protected String subject;
    protected String destinationEmail;

    //path to the freemarker file(.ftl)
    private String templateLocation;
    //template properties for freemarker template file
    private final Map<String, Object> templateProperties = new HashMap<>();

    public EmailDetails withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailDetails withDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
        return this;
    }

    public EmailDetails withTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
        return this;
    }

    public EmailDetails withTemplateProperty(String key, Object value) {
        templateProperties.put(key, value);
        return this;
    }
}
