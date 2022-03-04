package org.itransition.audit.mongo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "consumer_log")
public class ConsumerLog {

    @Id
    private String id;

    @Field(name = "time_stamp")
    private LocalDate timeStamp;

    @Field(name = "message")
    private String message;

    @Field(name = "name")
    private String firstName;

    @Field(name = "surname")
    private String lastName;
}
