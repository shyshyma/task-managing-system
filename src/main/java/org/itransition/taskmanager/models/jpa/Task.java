package org.itransition.taskmanager.models.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
@AttributeOverride(name = "id", column = @Column(name = "task_id"))
public class Task extends AbstractEntityLongId {

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = false, updatable = false)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_time", nullable = false)
    private Date expirationDate;

    @Column(name = "done_percentage", scale = 3, nullable = false)
    private Byte donePercentage;

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    public enum Status {
        NEW,
        IN_PROGRESS,
        FINISHED,
        ABANDONED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne(targetEntity = Consumer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id",
            foreignKey = @ForeignKey(name = "fk_task_consumer"),
            nullable = false, updatable = false)
    private Consumer consumer = new Consumer();

    @ElementCollection(targetClass = EmbeddableFile.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "attached_file",
            joinColumns = @JoinColumn(name = "task_id", updatable = false,
                    foreignKey = @ForeignKey(name = "fk_task_attached_file")))
    private List<EmbeddableFile> attachedFiles = new ArrayList<>();
}
