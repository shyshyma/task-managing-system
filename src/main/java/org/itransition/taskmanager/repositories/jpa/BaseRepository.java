package org.itransition.taskmanager.repositories.jpa;

import org.itransition.taskmanager.models.jpa.AbstractEntityLongId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<E extends AbstractEntityLongId> extends JpaRepository<E, Long> {
}
