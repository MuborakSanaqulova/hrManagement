package uz.pdp.hrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalaryDto {

    private Long id;

    private Double amount;

    private UserDto user;

    private Month day;

    private Boolean status;
}
