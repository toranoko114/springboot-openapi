package com.example.todoapi.controller.tasks;

import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.PageDto;
import com.example.todoapi.model.TaskDTO;
import com.example.todoapi.model.TaskDtoList;
import com.example.todoapi.model.TaskForm;
import com.example.todoapi.service.tasks.TaskEntity;
import com.example.todoapi.service.tasks.TaskService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

  private final TaskService taskService;

  @Override
  public ResponseEntity<TaskDTO> fetchTask(Long taskId) {
    var entity = taskService.fetch(taskId);
    // openapi-generatorで生成されるDtoにもLombok設定したい
    return ResponseEntity.ok(convertTaskDto(entity));
  }

  @Override
  public ResponseEntity<TaskDTO> createTask(TaskForm taskForm) {
    var entity = taskService.create(taskForm);
    var dto = convertTaskDto(entity);
    return ResponseEntity.created(URI.create("/tasks/" + dto.getId())).body(dto);
  }

  @Override
  public ResponseEntity<TaskDtoList> fetchTasks(Integer limit, Long offset) {
    var entityList = taskService.fetch(limit, offset);

    var page = new PageDto();
    page.limit(limit);
    page.offset(offset);
    page.setSize(entityList.size());

    var dto = new TaskDtoList();
    dto.setPage(page);
    dto.setResults(convertResults(entityList));
    return ResponseEntity.ok().body(dto);

  }

  @Override
  public ResponseEntity<TaskDTO> editTask(Long taskId, TaskForm taskForm) {
    var record = taskService.update(taskId, taskForm.getTitle());
    return ResponseEntity.ok(convertTaskDto(record));
  }

  @Override
  public ResponseEntity<Void> deleteTask(Long taskId) {
    taskService.delete(taskId);
    return ResponseEntity.noContent().build();
  }

  private static List<TaskDTO> convertResults(List<TaskEntity> entityList) {
    return entityList.stream()
        .map(TaskController::convertTaskDto)
        .collect(Collectors.toList());
  }

  private static TaskDTO convertTaskDto(TaskEntity entity) {
    var task = new TaskDTO();
    task.setId(entity.getId());
    task.setTitle(entity.getTitle());
    return task;
  }
}
