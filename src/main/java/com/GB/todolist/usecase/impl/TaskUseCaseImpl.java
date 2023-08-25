package com.GB.todolist.usecase.impl;

import com.GB.todolist.controller.dto.GetTaskModelListResponseDto;
import com.GB.todolist.controller.dto.UpdateTaskRequestDto;
import com.GB.todolist.exception.RequestGenericException;
import com.GB.todolist.model.TaskModel;
import com.GB.todolist.repository.TaskModelRepository;
import com.GB.todolist.usecase.TaskUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

//@Service
//@Slf4j
//public class TaskUseCaseImpl implements TaskUseCase {
//    @Autowired
//    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @Autowired
//    TaskModelRepository taskModelRepository;
//
//
//    @Override
//    public TaskModel getByTask(String task) {
//        log.info("Iniciando a busca pela task:{}", task);
//
//        TaskModel taskModel = taskModelRepository.getByTask(task);
//        if(taskModel == null) {
//            log.error("Task não encontrada!");
//            throw new RequestGenericException("404", "Task not found", "Task não encontrada!", 404);
//        }
//
//        return taskModel;
//    }
//
//    @Override
//    public GetTaskModelListResponseDto getTasks() {
//        log.info("Iniciando busca por todas as tasks");
//        List<TaskModel> taskModelList = taskModelRepository.findAll();
//
//        return GetTaskModelListResponseDto
//                .builder()
//                .taskModelList(taskModelList)
//                .build();
//    }
//
//    @Override
//    public Long sendTask(String task) {
//        if(taskExists(task)) {
//            log.error("A task '{}' já existe!", task);
//            throw new RequestGenericException("401", "Conflict", "A task já existe", 401);
//        }
//        try {
//            log.info("Enviando uma nova task");
//            TaskModel createTask = TaskModel
//                    .builder()
//                    .task(task)
//                    .done(false)
//                    .build();
//
//            taskModelRepository.save(createTask);
//            return createTask.getId();
//
//        } catch (Exception e) {
//            throw new RequestGenericException("400", "Erro ao enviar task", "Erro inesperado aconteceu, tente novamente", 400);
//        }
//    }
//
//    @Override
//    public boolean updateTask(UpdateTaskRequestDto updateTaskRequestDto) throws SQLException {
//        taskModelRepository.findById(updateTaskRequestDto.getId()).orElseThrow(() -> {
//            log.error("O id da task não existe!");
//            return new RequestGenericException("404", "Not Found", "O id da task não foi encontrado", 404);
//        });
//
//        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//        namedParameters.addValue("task", updateTaskRequestDto.getTask());
//        namedParameters.addValue("done", updateTaskRequestDto.getDone());
//
//        String sql = "UPDATE todolist SET task = :task, done = :done";
//        namedParameterJdbcTemplate.update(sql, namedParameters);
//
//        return true;
//
//    }
//
//    @Override
//    public boolean deleteTask(Long id) {
//        taskModelRepository.findById(id).orElseThrow(() -> {
//            log.error("Error ao deletar task, Id não encontrado!");
//            return new RequestGenericException("404", "Error ao deletar task", "Id da task não encontrado!", 404);
//        });
//
//        try {
//            log.info("Deletando a task de id: {}", id);
//            taskModelRepository.deleteById(id);
//            return true;
//        } catch (Exception e) {
//            log.error("Erro ao deletar a task: {}", id);
//            throw new RequestGenericException("400", "Erro ao deletar", "Erro inesperado durante a deleção, tente novamente!", 400);
//        }
//
//    }
//
//    private boolean taskExists(String task) {
//        log.info("Verificando se a task '{}' já existe", task);
//        TaskModel taskModel = taskModelRepository.getByTask(task);
//
//        return taskModel != null;
//    }
//
//}
