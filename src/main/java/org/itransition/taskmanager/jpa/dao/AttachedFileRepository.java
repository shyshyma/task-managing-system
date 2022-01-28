package org.itransition.taskmanager.jpa.dao;

import org.itransition.taskmanager.jpa.entity.AttachedFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachedFileRepository extends BaseRepository<AttachedFile> {

    boolean existsByName(String name);

    boolean existsByNameAndTaskIdAndTaskConsumerId(String name, Long taskId, Long consumerId);

    Optional<AttachedFile> findByNameAndTaskIdAndTaskConsumerId(String name, Long taskId, Long consumerId);

    Page<AttachedFile> findByTaskIdAndTaskConsumerId(Long taskId, Long consumerId, Pageable pageable);

    void deleteByNameAndTaskIdAndTaskConsumerId(String name, Long taskId, Long consumerId);
}
