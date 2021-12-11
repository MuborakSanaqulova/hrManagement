package uz.pdp.hrmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hrmanagement.model.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Transient
    private static final String seqName = "task_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = seqName)
    @SequenceGenerator(sequenceName = seqName, name = seqName, allocationSize = 1)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private User taskDoer;

    @ManyToOne
    private User taskGiver;

    private LocalDateTime deadline;

    private LocalDateTime doneTime;

    @Enumerated(EnumType.STRING)
    private Status status;

}
