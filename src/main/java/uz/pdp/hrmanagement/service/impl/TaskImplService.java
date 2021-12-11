package uz.pdp.hrmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.Task;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.model.enums.Status;
import uz.pdp.hrmanagement.payload.TaskDto;
import uz.pdp.hrmanagement.repository.TaskRepository;
import uz.pdp.hrmanagement.service.MailService;
import uz.pdp.hrmanagement.service.TaskService;
import uz.pdp.hrmanagement.service.mapper.TaskMapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskImplService implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    MailService mailService;

    @Override
    public Page<TaskDto> getAllTasks(Pageable pageable) {
        return getAllEntity(pageable).map(taskMapper::toDto);
    }

    @Override
    public Page<Task> getAllEntity(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Optional<TaskDto> getOne(Long id) {
        return taskRepository.findById(id).map(taskMapper::toDto);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public TaskDto saveTask(TaskDto taskDto, User taskDoer, User taskGiver) {

        Task task = taskMapper.toEntity(taskDto);
        task.setStatus(Status.NEW);
        task.setTaskDoer(taskDoer);
        task.setTaskGiver(taskGiver);

        Task task1 = saveTaskEntity(task);

        mailService.sendVerifyTask(taskDoer.getEmail(), taskDoer.getEmailCode());

        return taskMapper.toDto(task1);
    }

    @Override
    public Task saveTaskEntity(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void editTaskStatus(Task task) {
        task.setStatus(Status.DONE);
        task.setDoneTime(LocalDateTime.now());
        saveTaskEntity(task);
        mailService.sendDoneTaskMessage(task.getTaskGiver().getEmail(), task.getTaskGiver().getEmailCode());
    }


}
