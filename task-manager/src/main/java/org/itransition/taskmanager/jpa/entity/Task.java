package org.itransition.taskmanager.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.OneToMany;
import javax.persistence.Index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"consumer", "attachedFilesList"}, callSuper = true)
@Entity
@Table(name = "task",
        indexes = @Index(name = "ix_task_title", columnList = "title"),
        uniqueConstraints = {@UniqueConstraint(name = "uk_title", columnNames = {"title"})})
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "consumer_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_task_consumer"), nullable = false)
    private Consumer consumer;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<AttachedFile> attachedFilesList = new ArrayList<>();
}
