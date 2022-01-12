package org.itransition.taskmanager.services.jpa;

import org.itransition.taskmanager.models.jpa.Consumer;
import org.itransition.taskmanager.repositories.jpa.ConsumerRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService extends BaseService<Consumer, ConsumerRepository> {
}
