package com.GB.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todolist")
public class TaskModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "task", unique = true)
    private String task;

    @Column(name = "done")
    private Boolean done;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "id_user"), columnDefinition = "id")
    private UserModel userModel;
}
