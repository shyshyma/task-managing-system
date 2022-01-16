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
@Entity
@Table(name = "consumer", uniqueConstraints = @UniqueConstraint(name = "uk_consumer", columnNames = {"email"}))
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

    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> taskList = new ArrayList<>();
}
