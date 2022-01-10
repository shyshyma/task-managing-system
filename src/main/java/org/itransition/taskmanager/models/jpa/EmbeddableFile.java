package org.itransition.taskmanager.models.jpa;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmbeddableFile {

    @Lob
    @Column(name = "data", updatable = false, nullable = false, columnDefinition = "longblob")
    private byte[] data;

    @Column(name = "name", unique = true, nullable = false, columnDefinition = "varchar(100)")
    private String name;
}
