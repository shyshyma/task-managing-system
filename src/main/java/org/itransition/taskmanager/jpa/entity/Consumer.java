package org.itransition.taskmanager.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.itransition.taskmanager.jpa.listener.ConsumerListener;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.EntityListeners;
import javax.persistence.Index;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "taskList", callSuper = true)
@Entity
@Table(name = "consumer",
        indexes = @Index(name = "ix_consumer_email", columnList = "email"),
        uniqueConstraints = @UniqueConstraint(name = "uk_consumer", columnNames = {"email"}))
@EntityListeners(ConsumerListener.class)
public class Consumer extends AbstractEntityLongId {

    @Column(name = "first_name", nullable = false, columnDefinition = "varchar(40) default 'OMITTED'")
    private String firstName;

    @Column(name = "second_name", nullable = false, columnDefinition = "varchar(40) default 'OMITTED'")
    private String secondName;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", nullable = false, columnDefinition = "date")
    private Date dateOfBirth;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(40)")
    private String email;

    @OneToOne(mappedBy = "consumer", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private ConsumerConfig consumerConfig;

    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY)
    private List<Task> taskList = new ArrayList<>();
}
