package com.taskflow.task_service.Service;


import com.taskflow.task_service.Entity.TaskDetail;
import com.taskflow.task_service.Repository.TaskRepository;
import com.taskflow.task_service.Service.ClientService.FileServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    FileServiceClient fileServiceClient;

    public List<TaskDetail> getAllTasks() {
         return taskRepository.findAll();
    }

    public TaskDetail createTask(TaskDetail task , MultipartFile file) throws Exception {
         String attachmentId = fileServiceClient.uploadFileUsingFileService(file);
         task.setAttachmentId(attachmentId);
          return taskRepository.save(task);
    }
    public TaskDetail updateTask(TaskDetail task) {
        return taskRepository.save(task);
    }

    public TaskDetail getTask(int taskId)
    {
        return taskRepository.findById(taskId).orElse(null);
    }

    public ResponseEntity<Resource> getFileFromTask(int taskId)
    {
       String attachmentId =  taskRepository.findAttachmentIdByTaskId(taskId).orElseThrow();
        return fileServiceClient.downloadFileUsingFileService(attachmentId);
    }
}
