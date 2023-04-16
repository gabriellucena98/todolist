package com.GB.todolist.usecase.impl;

import com.GB.todolist.controller.dto.GetTaskModelListRequestDto;
import com.GB.todolist.exception.RequestGenericException;
import com.GB.todolist.model.TaskModel;
import com.GB.todolist.repository.TaskModelRepository;
import com.GB.todolist.usecase.TaskUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskUseCaseImpl implements TaskUseCase {

    @Autowired
    TaskModelRepository taskModelRepository;

    @Override
    public TaskModel getByTask(String task) {
        log.info("Iniciando a busca pela task:{}", task);

        TaskModel taskModel = taskModelRepository.findByTask(task);
        if(taskModel == null) {
            log.error("Task não encontrada!");
            throw new RequestGenericException("404", "Task not found", "Task não encontrada!", 404);
        }

        return taskModel;
    }

    @Override
    public GetTaskModelListRequestDto getTasks() {
        log.info("Iniciando busca por todas as tasks");

        List<TaskModel> taskModelList = taskModelRepository.findAll();

        return GetTaskModelListRequestDto
                .builder()
                .taskModelList(taskModelList)
                .build();
    }

    @Override
    public Long sendTask(String task) {
        if(taskExists(task)) {
            log.error("A task '{}' já existe!", task);
            throw new RequestGenericException("401", "Conflict", "A task já existe", 401);
        }

        log.info("Enviando uma nova task");
        TaskModel createTask = TaskModel
                .builder()
                .task(task)
                .done(false)
                .build();

        taskModelRepository.save(createTask);
        return createTask.getId();
    }

    private boolean taskExists(String task) {
        log.info("Verificando se a task '{}' já existe", task);
        TaskModel taskModel = taskModelRepository.findByTask(task);

        return taskModel == null;
    }
}
