package org.itransition.taskmanager.jpa.dao;

import org.itransition.taskmanager.jpa.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends BaseRepository<Task> {

    boolean existsByTitle(String title);

    boolean existsByIdAndConsumerId(Long id, Long consumerId);

    Optional<Task> findByIdAndConsumerId(Long id, Long consumerId);

    Page<Task> findByConsumerId(Long consumerId, Pageable pageable);

    void deleteByIdAndConsumerId(Long id, Long consumerId);

    @Query("SELECT t.title from Task t WHERE t.id = ?1")
    String findTaskTitleById(Long id);
}
