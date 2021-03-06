package uz.pdp.hrmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.Month;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MonthlySalary {
    @Transient
    private static final String seqName = "monthly_salary_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = seqName)
    @SequenceGenerator(sequenceName = seqName, name = seqName, allocationSize = 1)
    private Long id;

    private Double amount;

    private Instant paymentTime = Instant.now();

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Month month;

    private Boolean status;
}
