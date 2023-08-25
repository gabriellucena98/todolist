package com.GB.todolist.repository;

import com.GB.todolist.model.TaskModel;
import com.GB.todolist.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskModelRepository extends JpaRepository<TaskModel, Long> {

    @Transactional
    @Query(value = "SELECT * FROM todolist WHERE task = :task", nativeQuery = true)
    TaskModel getByTask(@Param("task") String task);

    List<TaskModel> findAllByUsername(UserModel userModel);
}
