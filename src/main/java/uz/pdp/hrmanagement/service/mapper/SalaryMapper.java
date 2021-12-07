package uz.pdp.hrmanagement.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.Salary;
import uz.pdp.hrmanagement.payload.SalaryDto;

@Service
public class SalaryMapper {

    @Autowired
    UserMapper userMapper;

    public SalaryDto toDto(Salary salary){

        SalaryDto salaryDto = new SalaryDto();
        salaryDto.setId(salary.getId());
        salaryDto.setAmount(salary.getAmount());
        salaryDto.setUser(userMapper.toDto(salary.getUser()));
        return salaryDto;
    }

}
