package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskImpl extends AbstractMapService<TaskDTO,Long> implements TaskService {
    @Override
    public TaskDTO save(TaskDTO object) {
        if(object.getStatus() == null){
            object.setStatus(Status.OPEN);
        }

        if(object.getAssignedDate() == null){
            object.setAssignedDate(LocalDate.now());
        }

        if(object.getId() == null){
            object.setId(UUID.randomUUID().getMostSignificantBits());
        }

        return super.save(object.getId(),object);
    }

    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(TaskDTO object) {
//        if(object.getStatus() == null){
//
//            object.setStatus(findById(object.getId()).getStatus());
//            object.setAssignedDate(findById(object.getId()).getAssignedDate());
//        }
        TaskDTO foundTask = findById(object.getId());

        object.setStatus(foundTask.getStatus());
        object.setAssignedDate(foundTask.getAssignedDate());
        super.update(object.getId(),object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<TaskDTO> findTaskByManager(UserDTO manager) {
        return findAll().stream()
                .filter(i -> i.getProject().getAssignedManager().equals(manager))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllPendingTasks() {
        return findAll().stream()
                .filter(i -> !i.getStatus().equals(Status.COMPLETE))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO task) {
        TaskDTO taskDTO = findById(task.getId());
        taskDTO.setStatus(task.getStatus());
        update(task);
    }


}
