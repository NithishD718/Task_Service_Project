package com.taskflow.task_service.Repository;

import com.taskflow.task_service.Entity.TaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskDetail, Integer> {
     Optional<TaskDetail> findAllByName(String name);
}
