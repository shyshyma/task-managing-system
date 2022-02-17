package org.itransition.taskmanager.jpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Id;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
@EqualsAndHashCode
public abstract class AbstractEntityLongId {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
