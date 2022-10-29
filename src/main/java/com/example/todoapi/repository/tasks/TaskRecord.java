package com.example.todoapi.repository.tasks;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskRecord {

  Long id;
  String title;
}
