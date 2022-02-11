package org.itransition.taskmanager.utils;

import com.github.javafaker.Faker;
import org.itransition.taskmanager.service.email.EmailDetails;

public final class EmailUtils {

    private EmailUtils() {
        throw new RuntimeException("This object cannot be created");
    }

    private static final Faker FAKER = new Faker();

    public static EmailDetails generateEmailDetailsByTemplateLocation(String templateLocation) {
        return new EmailDetails()
                .withDestinationEmail(FAKER.internet().emailAddress())
                .withSubject("Task manager test subject")
                .withTemplateLocation(templateLocation)
                .withTemplateProperty("name", FAKER.name().firstName())
                .withTemplateProperty("surname", FAKER.name().lastName());
    }
}
