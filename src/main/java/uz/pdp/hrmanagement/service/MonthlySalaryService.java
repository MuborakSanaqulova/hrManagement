package uz.pdp.hrmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.hrmanagement.model.MonthlySalary;
import uz.pdp.hrmanagement.model.Salary;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.MonthlySalaryDto;

import java.time.Month;
import java.util.Optional;

public interface MonthlySalaryService {

    Page<MonthlySalaryDto> getAll(Pageable pageable);

    Page<MonthlySalary> getAllEntity(Pageable pageable);

    Page<MonthlySalaryDto> getAllWorkersSalary(Pageable pageable);

    Page<MonthlySalary> getAllWorkersSalaryEntity(Pageable pageable);

    Optional<MonthlySalaryDto> findById(Long id);

    Optional<MonthlySalary> findByIdEntity(Long id);

    MonthlySalaryDto saveMonthlySalary(User user, Salary salary, MonthlySalaryDto monthlySalaryDto);

    MonthlySalary saveMonthlySalaryEntity(MonthlySalary monthlySalary);

    Optional<MonthlySalary> findByUserAndMonth(Long userId, Month month);
}
