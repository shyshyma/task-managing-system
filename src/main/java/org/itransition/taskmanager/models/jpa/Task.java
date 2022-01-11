package org.itransition.taskmanager.models.jpa;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task")
@AttributeOverride(name = "id", column = @Column(name = "task_id"))
public class Task extends AbstractEntityLongId {

    @Column(name = "title", unique = true, nullable = false, columnDefinition = "varchar(40)")
    private String title;

    @Column(name = "description", columnDefinition = "varchar(100)")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = false, updatable = false,
            insertable = false, columnDefinition = "timestamp")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_time", nullable = false, columnDefinition = "timestamp")
    private Date expirationDate;

    @Column(name = "done_percentage", scale = 3,
            nullable = false, columnDefinition = "tinyint")
    private Byte donePercentage;

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, columnDefinition = "varchar(25) default 'MEDIUM'")
    private Priority priority;

    public enum Status {
        NEW,
        IN_PROGRESS,
        FINISHED,
        ABANDONED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(25) default 'NEW'")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "consumer_id", referencedColumnName = "consumer_id",
            foreignKey = @ForeignKey(name = "fk_task_consumer"), nullable = false, updatable = false)
    private Consumer consumer;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "attached_file_to_task",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "task_id",
                    foreignKey = @ForeignKey(name = "fk_task_attached_file_to_task"),
                    nullable = false, updatable = false))
    private List<EmbeddableFile> attachedFilesList = new ArrayList<>();
}
