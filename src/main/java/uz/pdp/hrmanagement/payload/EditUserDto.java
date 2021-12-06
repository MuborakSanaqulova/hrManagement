package uz.pdp.hrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String password;
}
