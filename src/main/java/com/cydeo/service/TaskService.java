package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService extends CrudService<TaskDTO,Long>{
    List<TaskDTO> findTaskByManager(UserDTO manager);

    List<TaskDTO> findAllPendingTasks();

    void updateStatus(TaskDTO task);

    List<TaskDTO> findAllTaskByStatus(Status status);
}
