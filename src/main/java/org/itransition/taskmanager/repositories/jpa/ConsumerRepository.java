package org.itransition.taskmanager.repositories.jpa;

import org.itransition.taskmanager.models.jpa.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
}
