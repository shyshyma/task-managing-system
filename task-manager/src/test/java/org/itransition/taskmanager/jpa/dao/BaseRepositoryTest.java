package org.itransition.taskmanager.jpa.dao;

import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureDataJpa
@ActiveProfiles("test")
public abstract class BaseRepositoryTest {
}
