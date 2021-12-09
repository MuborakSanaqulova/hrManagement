package uz.pdp.hrmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.MonthlySalary;
import uz.pdp.hrmanagement.model.Salary;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.MonthlySalaryDto;
import uz.pdp.hrmanagement.repository.MonthlySalaryRepository;
import uz.pdp.hrmanagement.service.MonthlySalaryService;
import uz.pdp.hrmanagement.service.mapper.MonthlySalaryMapper;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@Service
public class MonthlySalaryServiceImpl implements MonthlySalaryService {

    @Autowired
    MonthlySalaryRepository monthlySalaryRepository;

    @Autowired
    MonthlySalaryMapper monthlySalaryMapper;

    @Override
    public Page<MonthlySalaryDto> getAll(Pageable pageable) {
        return getAllEntity(pageable).map(monthlySalaryMapper::toDto);
    }

    @Override
    public Page<MonthlySalary> getAllEntity(Pageable pageable) {
        return monthlySalaryRepository.findAll(pageable);
    }

    @Override
    public Page<MonthlySalaryDto> getAllWorkersSalary(Pageable pageable) {
        return getAllWorkersSalaryEntity(pageable).map(monthlySalaryMapper::toDto);
    }

    @Override
    public Page<MonthlySalary> getAllWorkersSalaryEntity(Pageable pageable) {
        return monthlySalaryRepository.findWorkersMonthlySalaries(pageable);
    }

    @Override
    public Optional<MonthlySalaryDto> findById(Long id) {
        return findByIdEntity(id).map(monthlySalaryMapper::toDto);
    }

    @Override
    public Optional<MonthlySalary> findByIdEntity(Long id) {
        return monthlySalaryRepository.findById(id);
    }

    @Override
    public MonthlySalaryDto saveMonthlySalary(User user, Salary salary, MonthlySalaryDto monthlySalaryDto) {

        MonthlySalary monthlySalary = new MonthlySalary();
        monthlySalary.setAmount(salary.getAmount());
        monthlySalary.setUser(user);
        monthlySalary.setStatus(true);
        monthlySalary.setMonth(LocalDate.now().getMonth());

        return monthlySalaryMapper.toDto(saveMonthlySalaryEntity(monthlySalary));
    }

    @Override
    public MonthlySalary saveMonthlySalaryEntity(MonthlySalary monthlySalary) {
        return monthlySalaryRepository.save(monthlySalary);
    }

    @Override
    public Optional<MonthlySalary> findByUserAndMonth(Long userId, Month month) {
        return monthlySalaryRepository.findByUserIdAndMonth(userId, month);
    }


}
