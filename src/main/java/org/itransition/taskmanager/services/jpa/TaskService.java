package org.itransition.taskmanager.services.jpa;

import org.itransition.taskmanager.models.jpa.Task;
import org.itransition.taskmanager.repositories.jpa.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends BaseService<Task, TaskRepository> {
}
