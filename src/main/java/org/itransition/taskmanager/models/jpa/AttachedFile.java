package org.itransition.taskmanager.models.jpa;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attached_file", uniqueConstraints = @UniqueConstraint(name = "uk_attached_file", columnNames = "name"))
public class AttachedFile extends AbstractEntityLongId {

    @Lob
    @Column(name = "data", nullable = false, columnDefinition = "longblob")
    private byte[] data;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_attached_file_task"), nullable = false)
    private Task task;
}
