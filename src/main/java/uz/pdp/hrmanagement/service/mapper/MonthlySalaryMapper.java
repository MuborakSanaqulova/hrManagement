package uz.pdp.hrmanagement.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.MonthlySalary;
import uz.pdp.hrmanagement.payload.MonthlySalaryDto;

@Service
public class MonthlySalaryMapper {

    @Autowired
    UserMapper userMapper;

    public MonthlySalaryDto toDto(MonthlySalary monthlySalary){

        MonthlySalaryDto monthlySalaryDto = new MonthlySalaryDto();
        monthlySalaryDto.setId(monthlySalary.getId());
        monthlySalaryDto.setAmount(monthlySalary.getAmount());
        monthlySalaryDto.setMonth(monthlySalary.getMonth());
        monthlySalaryDto.setStatus(monthlySalary.getStatus());
        monthlySalaryDto.setPaymentTime(monthlySalary.getPaymentTime());
        monthlySalaryDto.setUser(userMapper.toDto(monthlySalary.getUser()));

        return monthlySalaryDto;
    }

}
