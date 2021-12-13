package uz.pdp.hrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnpikeDto {

    private Long id;

    private LocalDateTime enterTime;

    private LocalDateTime exitTime;

    private LocalDate day;

    private UserDto userDto;

}
