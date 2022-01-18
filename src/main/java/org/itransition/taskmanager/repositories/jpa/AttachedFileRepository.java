package org.itransition.taskmanager.repositories.jpa;

import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachedFileRepository extends BaseRepository<AttachedFile> {

    boolean existsByNameAndTaskIdAndTaskConsumerId(String name, Long taskId, Long consumerId);

    Optional<AttachedFile> findByNameAndTaskIdAndTaskConsumerId(String name, Long taskId, Long consumerId);

    Page<AttachedFile> findByTaskIdAndTaskConsumerId(Long taskId, Long consumerId, Pageable pageable);

    void deleteByNameAndTaskIdAndTaskConsumerId(String name, Long taskId, Long consumerId);
}
