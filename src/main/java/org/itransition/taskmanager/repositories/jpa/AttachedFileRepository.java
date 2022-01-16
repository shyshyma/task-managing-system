package org.itransition.taskmanager.repositories.jpa;

import org.itransition.taskmanager.models.jpa.AttachedFile;
import org.itransition.taskmanager.models.jpa.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachedFileRepository extends BaseRepository<AttachedFile> {

    AttachedFile findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);

    Page<AttachedFile> findByTaskId(Long taskId, Pageable pageable);
}
