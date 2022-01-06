package org.itransition.todolist.models.jpa;

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
@Table(name = "consumer")
@AttributeOverride(name = "id", column = @Column(name = "consumer_id"))
public class Consumer extends AbstractEntityLongId {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToMany(targetEntity = Task.class, mappedBy = "consumer",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> taskList = new ArrayList<>();
}