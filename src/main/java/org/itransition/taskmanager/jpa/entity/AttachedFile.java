package org.itransition.taskmanager.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Lob;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "name", callSuper = true)
@Table(name = "attached_file", uniqueConstraints = @UniqueConstraint(name = "uk_attached_file", columnNames = "name"))
public class AttachedFile extends AbstractEntityLongId {

    @Lob
    @Column(name = "data", nullable = false, columnDefinition = "longblob")
    private byte[] data;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "task_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_attached_file_task"), nullable = false)
    private Task task;
}
