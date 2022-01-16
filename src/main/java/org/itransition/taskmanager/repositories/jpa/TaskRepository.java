package org.itransition.taskmanager.repositories.jpa;

import org.itransition.taskmanager.models.jpa.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends BaseRepository<Task> {

    Page<Task> findByConsumerId(Long consumerId, Pageable pageable);
}
