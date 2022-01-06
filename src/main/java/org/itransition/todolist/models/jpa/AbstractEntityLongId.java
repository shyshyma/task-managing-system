package org.itransition.todolist.models.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Needed in order not to create an entity identifier, instead,
 * you can inherit from this class.
 * Also, you can redefine the column name, if you need
 *
 * @see AttributeOverride
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntityLongId {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}