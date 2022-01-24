package org.itransition.taskmanager.jpa.dao;

import org.itransition.taskmanager.jpa.entity.AbstractEntityLongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E extends AbstractEntityLongId> extends JpaRepository<E, Long> {
}
