package org.itransition.taskmanager.repositories.jpa;

import org.itransition.taskmanager.models.jpa.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
