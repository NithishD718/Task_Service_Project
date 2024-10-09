package com.taskflow.task_service.Controller;

import com.taskflow.task_service.Entity.TaskDetail;
import com.taskflow.task_service.Service.ClientService.EmailServiceClient;
import com.taskflow.task_service.Service.TaskService;
import com.taskflow.task_service.Service.ClientService.UserClient;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/get")
    public List<TaskDetail> GetAllTasks()
    {
        return taskService.getAllTasks();
    }
    @PostMapping("/create")
    public ResponseEntity<String> CreateTask(@RequestPart("taskRequest") TaskDetail taskRequest,@RequestPart("file") MultipartFile file) throws Exception {
        String toMail = userClient.getUserEmail(taskRequest.getUserId()); //While geting username userservice passes login html page from filter so error comes
        String subject = "Task Created";
        String body = "A new task has been created";
        TaskDetail taskDetail = taskService.createTask(taskRequest,file);
        logger.info("Task Created Successfully");
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
        logger.debug("Task Updated Successfully");
        return ResponseEntity.ok("Finished");
    }

    @GetMapping("/view/{taskId}")
    public ResponseEntity<TaskDetail> viewTask(@PathVariable int taskId)
    {
       TaskDetail taskDetail =  taskService.getTask(taskId);
       if(taskDetail!=null)
           return ResponseEntity.ok(taskDetail);
       else
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/downloadFile/{taskId}")
    public ResponseEntity<Resource> downloadTaskFile(@PathVariable int taskId)
    {
       return taskService.getFileFromTask(taskId);
    }

}
