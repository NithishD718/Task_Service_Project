package com.taskflow.task_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EmailDetail {
    @Id
    private String toMail;
    private String subject;
    private  String body;
}
