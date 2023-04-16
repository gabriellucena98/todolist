package com.GB.todolist.repository;

import com.GB.todolist.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskModelRepository extends JpaRepository<TaskModel, Long> {

    TaskModel findByTask(String task);
}
