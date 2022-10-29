package com.example.todoapi.repository.tasks;

import com.example.todoapi.model.TaskForm;
import com.example.todoapi.service.tasks.TaskEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TaskRepository {

  @Select("SELECT id, title FROM tasks WHERE id = #{taskId}")
  Optional<TaskRecord> select(long taskId);

  @Insert("INSERT INTO tasks (title) VALUES (#{title})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(TaskRecord record);

  @Select("SELECT id, title FROM tasks LIMIT #{limit} OFFSET #{offset}")
  List<TaskRecord> selectList(Integer limit, Long offset);

  @Update("UPDATE tasks set title = #{title} where id = #{id}")
  void update(TaskRecord record);

  @Delete("delete from tasks where id = #{taskId}")
  void delete(Long taskId);
}
