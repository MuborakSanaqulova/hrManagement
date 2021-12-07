package uz.pdp.hrmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.Salary;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.SalaryDto;
import uz.pdp.hrmanagement.repository.SalaryRepository;
import uz.pdp.hrmanagement.service.SalaryService;
import uz.pdp.hrmanagement.service.mapper.SalaryMapper;

import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    SalaryMapper salaryMapper;

    @Override
    public SalaryDto postSalary(SalaryDto salaryDto, User user) {

        Salary salary = new Salary();
        salary.setAmount(salaryDto.getAmount());
        salary.setUser(user);
        return salaryMapper.toDto(saveSalary(salary));
    }

    @Override
    public Salary saveSalary(Salary salary) {
        return salaryRepository.save(salary);
    }

    @Override
    public boolean existByUserId(Long id) {
        return salaryRepository.existsByUserId(id);
    }

    @Override
    public Optional<Salary> findById(Long id) {
       return salaryRepository.findById(id);
    }

    @Override
    public SalaryDto editSalary(Double amount, Salary salary) {
        salary.setAmount(amount);
        return salaryMapper.toDto(saveSalary(salary));
    }

    @Override
    public Optional<Salary> findWorker(Long id) {
        return salaryRepository.findWorkerByUserId(id);
    }

    @Override
    public Page<SalaryDto> findAll(Pageable pageable) {
        return findAllEntity(pageable).map(salaryMapper::toDto);
    }

    @Override
    public Page<Salary> findAllEntity(Pageable pageable) {
        return salaryRepository.findAll(pageable);
    }

    @Override
    public Page<SalaryDto> findWorkers(Pageable pageable) {
        return findWorkersEntity(pageable).map(salaryMapper::toDto);
    }

    @Override
    public Page<Salary> findWorkersEntity(Pageable pageable) {
        return salaryRepository.findWorkersPage(pageable);
    }

    @Override
    public Optional<SalaryDto> findByIdDto(Long id) {
        return findById(id).map(salaryMapper::toDto);
    }


}
