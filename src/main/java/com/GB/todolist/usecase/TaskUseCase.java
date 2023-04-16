package com.GB.todolist.usecase;

import com.GB.todolist.controller.dto.GetTaskModelListRequestDto;
import com.GB.todolist.model.TaskModel;

public interface TaskUseCase {

    TaskModel getByTask(String task);

    GetTaskModelListRequestDto getTasks();

    Long sendTask(String task);
}
