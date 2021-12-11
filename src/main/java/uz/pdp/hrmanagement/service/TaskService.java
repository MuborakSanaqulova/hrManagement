package uz.pdp.hrmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.hrmanagement.model.Task;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.TaskDto;

import java.util.Optional;

public interface TaskService {

    Page<TaskDto> getAllTasks(Pageable pageable);

    Page<Task> getAllEntity(Pageable pageable);

    Optional<TaskDto> getOne(Long id);

    Optional<Task> findById(Long id);

    TaskDto saveTask(TaskDto taskDto, User taskDoer, User taskGiver);

    Task saveTaskEntity(Task task);

    void editTaskStatus(Task task);
}
