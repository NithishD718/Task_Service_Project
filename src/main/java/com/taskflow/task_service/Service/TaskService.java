package com.taskflow.task_service.Service;


import com.taskflow.task_service.Entity.TaskDetail;
import com.taskflow.task_service.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    public List<TaskDetail> getAllTasks() {
         return taskRepository.findAll();
    }

    public TaskDetail createTask(TaskDetail task) {
          return taskRepository.save(task);
    }
    public TaskDetail updateTask(TaskDetail task) {
        return taskRepository.save(task);
    }
}
