package com.taskflow.task_service.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
public class TaskDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true , nullable = false)
    private String name;
    private String description;
    private Boolean isCompleted;
    @Getter
    private Integer userId;
    private String attachmentId;
}
