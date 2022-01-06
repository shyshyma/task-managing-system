package org.itransition.todolist.repositories.jpa;

import org.itransition.todolist.models.jpa.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
}
