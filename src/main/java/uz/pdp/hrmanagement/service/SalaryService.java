package uz.pdp.hrmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.hrmanagement.model.Salary;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.SalaryDto;

import java.util.Optional;

public interface SalaryService {

    SalaryDto postSalary(SalaryDto salaryDto, User user);

    Salary saveSalary(Salary salary);

    boolean existByUserId(Long id);

    Optional<Salary> findById(Long id);

    SalaryDto editSalary(Double amount, Salary salary);

    Optional<Salary> findWorker(Long id);

    Page<SalaryDto> findAll(Pageable pageable);

    Page<Salary> findAllEntity(Pageable pageable);

    Page<SalaryDto> findWorkers(Pageable pageable);

    Page<Salary> findWorkersEntity(Pageable pageable);

    Optional<SalaryDto> findByIdDto(Long id);

    Optional<Salary> findByUserId(Long userId);
}
