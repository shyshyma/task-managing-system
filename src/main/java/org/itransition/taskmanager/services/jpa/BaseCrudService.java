package org.itransition.taskmanager.services.jpa;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.itransition.taskmanager.exceptions.ModelNotFoundException;
import org.itransition.taskmanager.models.jpa.AbstractEntityLongId;
import org.itransition.taskmanager.repositories.jpa.BaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseCrudService<E extends AbstractEntityLongId, R extends BaseRepository<E>> {

    protected static final String ENTITY_IDENTIFIER_COLUMN_NAME = "id";

    @Setter(onMethod_ = @Autowired)
    protected R repository;

    public boolean existsById(Long id) {
        log.info("Verifying that entity exists by id {} inside of the JPA datastore unit", id);
        return repository.existsById(id);
    }

    public E save(E o) {
        log.info("Saving entity to the JPA datastore unit");
        return repository.save(o);
    }

    public <T> T save(E e, Function<E, T> mapper) {
        E savedEntity = save(e);
        return mapper.apply(savedEntity);
    }

    public E findById(Long id) {
        log.info("Fetching entity with id {} from the JPA datastore unit", id);
        return repository.findById(id).orElseThrow(() -> new ModelNotFoundException(id));
    }

    public <T> T findById(Long id, Function<E, T> mapper) {
        return mapper.apply(findById(id));
    }

    public <T> T update(E e, Function<E, T> mapper, String... ignoredProperties) {
        E updatedEntity = update(e, ignoredProperties);
        return mapper.apply(updatedEntity);
    }

    public E update(E e, String... ignoredProperties) {
        log.info("Updating JPA entity with id {}", e.getId());

        E entityById = repository.findById(e.getId())
                .orElseThrow(() -> new ModelNotFoundException("there is no entity with id: " + e.getId()));
        BeanUtils.copyProperties(e, entityById, ignoredProperties);
        return repository.save(entityById);
    }

    public Page<E> findPage(Pageable pageable) {
        log.info("Fetching page of entities from the JPA datastore unit");

        return repository.findAll(pageable);
    }

    public <T> Page<T> findPage(Pageable pageable, Function<E, T> mapper) {
        log.info("Fetching page of entities from the JPA datastore unit");

        Page<E> entitiesPage = repository.findAll(pageable);
        return new PageImpl<>(entitiesPage.getContent().stream()
                .map(mapper).collect(toList()), pageable, entitiesPage.getTotalElements());
    }

    public List<E> findPage(Boolean asc, String... sortBy) {
        log.info("Fetching page of entities from the JPA datastore unit");

        if (Objects.isNull(sortBy) || sortBy.length == 0) {
            sortBy = new String[]{ENTITY_IDENTIFIER_COLUMN_NAME};
        }

        Sort.Direction direction = Boolean.TRUE.equals(asc) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return repository.findAll(Sort.by(direction, sortBy));
    }

    public void deleteById(Long id) {

        if (!repository.existsById(id)) {
            throw new ModelNotFoundException("there is no entity with id: " + id);
        }

        log.info("Deleting entity with id {} from the JPA datastore unit", id);

        repository.deleteById(id);
    }
}
