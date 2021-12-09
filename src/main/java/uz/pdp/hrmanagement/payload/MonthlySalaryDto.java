package uz.pdp.hrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.Month;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalaryDto {

    private Long id;

    private Instant paymentTime;

    private Double amount;

    private UserDto user;

    private Month month;

    private Boolean status;
}
