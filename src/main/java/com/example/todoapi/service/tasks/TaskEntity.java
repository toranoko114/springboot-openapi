package com.example.todoapi.service.tasks;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskEntity {

  Long id;
  String title;

}
