package org.itransition.taskmanager.models.jpa;

import lombok.*;
import org.itransition.taskmanager.enums.Priority;
import org.itransition.taskmanager.enums.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "title", callSuper = false)
@Entity
@Table(name = "task", uniqueConstraints = {@UniqueConstraint(name = "uk_title", columnNames = {"title"})})
public class Task extends AbstractEntityLongId {

    @Column(name = "title", nullable = false, columnDefinition = "varchar(40)")
    private String title;

    @Column(name = "description", columnDefinition = "varchar(100)")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = false, updatable = false, columnDefinition = "timestamp")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_time", nullable = false, columnDefinition = "timestamp")
    private Date expirationDate;

    @Column(name = "done_percentage", scale = 3,
            nullable = false, columnDefinition = "tinyint")
    private Integer donePercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, columnDefinition = "varchar(25) default 'MEDIUM'")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(25) default 'NEW'")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "consumer_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_task_consumer"), nullable = false)
    private Consumer consumer;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttachedFile> attachedFilesList = new ArrayList<>();
}
