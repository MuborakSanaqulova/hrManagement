package uz.pdp.hrmanagement.payload;

import lombok.Data;
import uz.pdp.hrmanagement.model.enums.RoleName;

@Data
public class ChangeRoleDto {
    private RoleName roleName;
}
