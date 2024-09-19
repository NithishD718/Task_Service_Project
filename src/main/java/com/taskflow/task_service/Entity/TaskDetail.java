package com.taskflow.task_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

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
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }
}
