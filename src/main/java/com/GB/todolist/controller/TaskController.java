package com.GB.todolist.controller;

import com.GB.todolist.controller.dto.GenericResponseWithIdDto;
import com.GB.todolist.controller.dto.GetTaskModelListResponseDto;
import com.GB.todolist.controller.dto.UpdateTaskRequestDto;
import com.GB.todolist.model.TaskModel;
import com.GB.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/getByTask")
    public ResponseEntity<TaskModel> getByTask(@RequestParam("task") String task) {
        return ok(taskService.getByTask(task));
    }

    @GetMapping("/getTasks")
    public ResponseEntity<GetTaskModelListResponseDto> getTasks() {
        return ok(taskService.getTasks());
    }

    @PostMapping(value = "/sendTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponseWithIdDto> sendTask(@RequestParam("task") String task) {
        Long taskId = taskService.sendTask(task);
        return ok(GenericResponseWithIdDto.builder().code("200").title("/sendTask").title("Task criada com sucesso!").id(taskId).build());
    }

    @PutMapping("/updateTask")
    public ResponseEntity<GenericResponseWithIdDto> updateTask(@RequestBody UpdateTaskRequestDto updateTaskRequestDto) throws SQLException {
        taskService.updateTask(updateTaskRequestDto);
        return ok(GenericResponseWithIdDto.builder().code("200").title("/updateTask").detail("Task atualizada com sucesso!").id(updateTaskRequestDto.getId()).build());
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<GenericResponseWithIdDto> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ok(GenericResponseWithIdDto.builder().code("200").title("/deleteTask/{id}").detail("Task deletada com sucesso!").id(id).build());
    }

}
