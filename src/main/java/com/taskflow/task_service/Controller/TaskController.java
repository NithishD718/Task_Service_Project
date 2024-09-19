package com.taskflow.task_service.Controller;

import com.taskflow.task_service.Entity.TaskDetail;
import com.taskflow.task_service.Service.EmailServiceClient;
import com.taskflow.task_service.Service.TaskService;
import com.taskflow.task_service.Service.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.lang.String.format;

@RequestMapping("/task")
@RestController
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    EmailServiceClient emailServiceClient;
    @Autowired
    UserClient userClient;

    @GetMapping("/get")
    public List<TaskDetail> GetAllTasks()
    {
        return taskService.getAllTasks();
    }
    @PostMapping("/create")
    public ResponseEntity<String> CreateTask(@RequestBody TaskDetail task)
    {
        String toMail = userClient.getUserEmail(task.getUserId()); //While geting username userservice passes login html page from filter so error comes
        String subject = "Task Created";
        String body = "A new task has been created";
        TaskDetail taskDetail = taskService.createTask(task);
        return emailServiceClient.sendEmail(toMail,subject,body);
    }
    @PutMapping("/update")
    public ResponseEntity<String> UpdateTask(@RequestBody TaskDetail task)
    {
        String toMail = "nithishdani2000@gmail.com";
        String subject = "Task Updated";
        String body = "This task has been updated.";
        TaskDetail taskDetail = taskService.updateTask(task);
       // return emailServiceClient.sendEmail(toMail,subject,body);
        emailServiceClient.sentEmailUsingWebClient(toMail,subject,body);
        return ResponseEntity.ok("Finished");
    }

}
