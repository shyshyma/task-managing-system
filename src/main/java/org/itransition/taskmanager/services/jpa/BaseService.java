package org.itransition.taskmanager.services.jpa;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.itransition.taskmanager.exceptions.JpaModelNotFoundException;
import org.itransition.taskmanager.models.jpa.AbstractEntityLongId;
import org.itransition.taskmanager.repositories.jpa.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public abstract class BaseService<E extends AbstractEntityLongId, R extends BaseRepository<E>> {

    @Setter(onMethod_ = @Autowired)
    protected R repository;

    public E save(E o) {
        return repository.save(o);
    }

    public void delete(E o) {
        repository.delete(o);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    public E getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new JpaModelNotFoundException(id));
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public List<E> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<E> findAll(Specification<E> specification) {
        return repository.findAll(specification);
    }

    public List<E> findAll(Specification<E> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    public Page<E> findAll(Specification<E> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    public <T> Page<T> findAll(Specification<E> specification, Pageable pageable, Function<E, T> mapper) {
        return mapPageResults(repository.findAll(specification, pageable), pageable, mapper);
    }

    public <T> Page<T> findAll(Pageable pageable, Function<E, T> mapper) {
        return mapPageResults(repository.findAll(pageable), pageable, mapper);
    }

    public <T> List<T> findAll(Specification<E> specification, Sort sort, Function<E, T> mapper) {
        return mapListResults(repository.findAll(specification, sort), mapper);
    }

    public <T> List<T> findAll(Function<E, T> mapper) {
        return mapListResults(repository.findAll(), mapper);
    }

    public <T> List<T> findAll(Sort sort, Function<E, T> mapper) {
        return mapListResults(repository.findAll(sort), mapper);
    }

    public <T> List<T> findAll(Specification<E> specification, Function<E, T> mapper) {
        return mapListResults(repository.findAll(specification), mapper);
    }

    public List<E> findAll(Boolean asc, String sortBy) {
        Sort.Direction direction = Boolean.TRUE.equals(asc) ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (StringUtils.isBlank(sortBy)) {
            sortBy = getDefaultSortColumn();
        }
        return repository.findAll(Sort.by(direction, sortBy));
    }

    public List<E> findAll(Boolean asc, String... sortBy) {
        Sort.Direction direction = Boolean.TRUE.equals(asc) ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (sortBy == null || sortBy.length == 0) {
            sortBy = new String[]{getDefaultSortColumn()};
        }
        return repository.findAll(Sort.by(direction, sortBy));
    }

    private <T> Page<T> mapPageResults(Page<E> page, Pageable pageable, Function<E, T> mapper) {
        return new PageImpl<>(page.getContent().stream().map(mapper).collect(toList()), pageable, page.getTotalElements());
    }

    private <T> List<T> mapListResults(List<E> list, Function<E, T> mapper) {
        return list.stream().map(mapper).collect(toList());
    }

    String getDefaultSortColumn() {
        return "id";
    }
}
