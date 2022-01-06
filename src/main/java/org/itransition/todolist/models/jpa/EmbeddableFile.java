package org.itransition.todolist.models.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmbeddableFile {

    @Lob
    @Column(name = "data", updatable = false, nullable = false)
    private byte[] data;

    @Column(name = "name", updatable = false, nullable = false)
    private String name;
}
