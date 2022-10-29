package com.example.todoapi.service.tasks;

import com.example.todoapi.model.TaskForm;
import com.example.todoapi.repository.tasks.TaskRecord;
import com.example.todoapi.repository.tasks.TaskRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskEntity fetch(Long taskId) {
    return taskRepository.select(taskId)
        .map(record ->
            TaskEntity
                .builder()
                .id(record.getId())
                .title(record.getTitle())
        )
        .orElseThrow(() -> new TaskEntityNotFoundException(taskId))
        .build();
  }

  public TaskEntity create(TaskForm taskForm) {
    var record = TaskRecord.builder().id(null).title(taskForm.getTitle()).build();
    taskRepository.insert(record);
    return TaskEntity.builder().id(record.getId()).title(record.getTitle()).build();
  }

  public List<TaskEntity> fetch(Integer limit, Long offset) {
    return taskRepository.selectList(limit, offset).stream()
        .map(record -> new TaskEntity(record.getId(), record.getTitle()))
        .collect(Collectors.toList());
  }

  public TaskEntity update(Long taskId, String title) {
    // レコードが存在するか事前にチェック. 存在しない場合はエラーをthrow.
    taskRepository.select(taskId)
        .orElseThrow(() -> new TaskEntityNotFoundException(taskId));
    // 更新する
    taskRepository.update(TaskRecord.builder().id(taskId).title(title).build());
    return this.fetch(taskId);
  }

  public void delete(Long taskId) {
    taskRepository.select(taskId)
        .orElseThrow(() -> new TaskEntityNotFoundException(taskId));
    taskRepository.delete(taskId);
  }
}
