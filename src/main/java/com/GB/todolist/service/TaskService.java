package com.GB.todolist.service;

import com.GB.todolist.controller.dto.GetTaskModelListResponseDto;
import com.GB.todolist.controller.dto.UpdateTaskRequestDto;
import com.GB.todolist.exception.RequestGenericException;
import com.GB.todolist.model.TaskModel;
import com.GB.todolist.model.UserModel;
import com.GB.todolist.repository.TaskModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TaskService {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    TaskModelRepository taskModelRepository;

    @Autowired
    private UserService userService;

    public TaskModel getByTask(String task) {
        log.info("Iniciando a busca pela task:{}", task);

        TaskModel taskModel = taskModelRepository.getByTask(task);
        if(taskModel == null) {
            log.error("Task não encontrada!");
            throw new RequestGenericException("404", "Task not found", "Task não encontrada!", 404);
        }

        return taskModel;
    }

    public GetTaskModelListResponseDto getTasks() {
        UserModel userModel = userService.getAuthenticatedUser();

        log.info("Iniciando busca por todas as tasks do usuário: {}", userModel.getUsername());
        List<TaskModel> taskModelList = taskModelRepository.findAllByUsername(userModel);

        return GetTaskModelListResponseDto
                .builder()
                .taskModelList(taskModelList)
                .build();
    }

    @Transactional
    public Long sendTask(String task) {
        if(taskExists(task)) {
            log.error("A task '{}' já existe!", task);
            throw new RequestGenericException("401", "Conflict", "A task já existe", 401);
        }
        try {
            UserModel userModel = userService.getAuthenticatedUser();

            log.info("Enviando uma nova task");
            TaskModel createTask = TaskModel
                    .builder()
                    .task(task)
                    .done(false)
                    .userModel(userModel)
                    .build();

            taskModelRepository.save(createTask);
            return createTask.getId();

        } catch (Exception e) {
            throw new RequestGenericException("400", "Erro ao enviar task", "Erro inesperado aconteceu, tente novamente", 400);
        }
    }

    public boolean updateTask(UpdateTaskRequestDto updateTaskRequestDto) throws SQLException {
        taskModelRepository.findById(updateTaskRequestDto.getId()).orElseThrow(() -> {
            log.error("O id da task não existe!");
            return new RequestGenericException("404", "Not Found", "O id da task não foi encontrado", 404);
        });

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("task", updateTaskRequestDto.getTask());
        namedParameters.addValue("done", updateTaskRequestDto.getDone());

        String sql = "UPDATE todolist SET task = :task, done = :done";
        namedParameterJdbcTemplate.update(sql, namedParameters);

        return true;

    }

    public boolean deleteTask(Long id) {
        taskModelRepository.findById(id).orElseThrow(() -> {
            log.error("Error ao deletar task, Id não encontrado!");
            return new RequestGenericException("404", "Error ao deletar task", "Id da task não encontrado!", 404);
        });

        try {
            log.info("Deletando a task de id: {}", id);
            taskModelRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Erro ao deletar a task: {}", id);
            throw new RequestGenericException("400", "Erro ao deletar", "Erro inesperado durante a deleção, tente novamente!", 400);
        }

    }

    private boolean taskExists(String task) {
        log.info("Verificando se a task '{}' já existe", task);
        TaskModel taskModel = taskModelRepository.getByTask(task);

        return taskModel != null;
    }
}
