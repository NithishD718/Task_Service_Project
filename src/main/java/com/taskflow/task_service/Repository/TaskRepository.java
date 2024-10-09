package com.taskflow.task_service.Repository;

import com.taskflow.task_service.Entity.TaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskDetail, Integer> {
     Optional<TaskDetail> findAllByName(String name);

     @Query("SELECT t.attachmentId FROM TaskDetail t WHERE t.id =:taskId")
     Optional<String> findAttachmentIdByTaskId(@Param("taskId") int taskId);
}

