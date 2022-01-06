package org.itransition.todolist.repositories.jpa;

import org.itransition.todolist.models.jpa.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
