package com.GB.todolist.controller;

import com.GB.todolist.controller.dto.GenericResponseWithIdDto;
import com.GB.todolist.controller.dto.GetTaskModelListRequestDto;
import com.GB.todolist.model.TaskModel;
import com.GB.todolist.usecase.TaskUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskUseCase taskUseCase;

    @GetMapping("/getByTask")
    public ResponseEntity<TaskModel> getByTask(@RequestParam("task") String task) {
        return ok(taskUseCase.getByTask(task));
    }

    @GetMapping("/getTasks")
    public ResponseEntity<GetTaskModelListRequestDto> getTasks() {
        return ok(taskUseCase.getTasks());
    }

    @PostMapping("/sendTask")
    public ResponseEntity<GenericResponseWithIdDto> sendTask(@RequestParam("task") String task) {
        Long taskId = taskUseCase.sendTask(task);
        return ok(GenericResponseWithIdDto.builder().code("200").title("/sendTask").title("Task criada com sucesso!").id(taskId).build());
    }

}
