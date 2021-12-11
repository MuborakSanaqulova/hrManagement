package uz.pdp.hrmanagement.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.Task;
import uz.pdp.hrmanagement.payload.TaskDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

@Service
public class TaskMapper {

    @Autowired
    UserMapper userMapper;

    public TaskDto toDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTaskDoer(userMapper.toDto(task.getTaskDoer()));
        taskDto.setTaskGiver(userMapper.toDto(task.getTaskGiver()));
        taskDto.setDescription(task.getDescription());
        taskDto.setDeadline(task.getDeadline().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        taskDto.setDoneTime(task.getDoneTime());
        taskDto.setName(task.getName());
        taskDto.setStatus(task.getStatus());

        return taskDto;
    }

    public Task toEntity(TaskDto taskDto){
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDeadline(LocalDateTime.ofInstant(Instant.ofEpochMilli(taskDto.getDeadline()), TimeZone
                .getDefault().toZoneId()));
        return task;
    }

}
