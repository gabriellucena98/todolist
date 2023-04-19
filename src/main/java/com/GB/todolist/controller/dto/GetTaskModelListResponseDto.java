package com.GB.todolist.controller.dto;

import com.GB.todolist.model.TaskModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskModelListResponseDto {

    private List<TaskModel> taskModelList;
}
