package uz.pdp.hrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hrmanagement.model.enums.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;

    private String name;

    private String description;

    private UserDto taskDoer;

    private UserDto taskGiver;

    private Long deadline;

    private LocalDateTime doneTime;

    private Status status;

}
