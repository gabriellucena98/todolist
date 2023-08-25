package com.GB.todolist.controller.dto;

import com.GB.todolist.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUsersResponseDto {

    private List<UserModel> userModelList;
}
