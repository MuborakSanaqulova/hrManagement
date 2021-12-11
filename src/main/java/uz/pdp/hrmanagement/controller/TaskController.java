package uz.pdp.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagement.common.ResponseData;
import uz.pdp.hrmanagement.model.Task;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.TaskDto;
import uz.pdp.hrmanagement.service.TaskService;
import uz.pdp.hrmanagement.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<ResponseData<Page<TaskDto>>> getAllTasks(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseData.response(taskService.getAllTasks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<TaskDto>> getTask(@PathVariable Long id){

        Optional<TaskDto> taskDto = taskService.getOne(id);
        if (taskDto.isEmpty())
            return ResponseData.response("task not found", HttpStatus.BAD_REQUEST);

        return ResponseData.response(taskDto.get());

    }

    @PostMapping
    public ResponseEntity<?> saveTask(@RequestBody TaskDto taskDto){
//        Optional<Task> optionalTask = taskService.findById(taskDto.getId());
//        if (optionalTask.isPresent())
//            return ResponseData.response("already exist", HttpStatus.BAD_REQUEST);

        Optional<User> taskGiver = userService.findById(taskDto.getTaskGiver().getId());
        if (taskGiver.isEmpty())
            return ResponseData.response("Task giver not found", HttpStatus.BAD_REQUEST);

        Optional<User> taskDoer = userService.findById(taskDto.getTaskDoer().getId());
        if (taskDoer.isEmpty())
            return ResponseData.response("task doer not found", HttpStatus.BAD_REQUEST);

        return ResponseData.response(taskService.saveTask(taskDto, taskDoer.get(), taskGiver.get()));
    }

    @PutMapping("/forDoer/{id}")
    public ResponseEntity<?> editTaskStatus(@PathVariable Long id){
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty())
            return ResponseData.response("task not found", HttpStatus.BAD_REQUEST);

        taskService.editTaskStatus(optionalTask.get());
        return ResponseData.response("task bajarilganida xabar", HttpStatus.ACCEPTED);
    }

}
