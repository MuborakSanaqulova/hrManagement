package uz.pdp.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagement.common.ResponseData;
import uz.pdp.hrmanagement.model.MonthlySalary;
import uz.pdp.hrmanagement.model.Salary;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.MonthlySalaryDto;
import uz.pdp.hrmanagement.service.MonthlySalaryService;
import uz.pdp.hrmanagement.service.SalaryService;
import uz.pdp.hrmanagement.service.UserService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("api/monthlySalary")
public class MonthlySalaryController {

    @Autowired
    MonthlySalaryService monthlySalaryService;

    @Autowired
    UserService userService;

    @Autowired
    SalaryService salaryService;

    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    @GetMapping
    public ResponseEntity<ResponseData<Page<MonthlySalaryDto>>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseData.response(monthlySalaryService.getAll(pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR','ROLE_HR_MANAGER')")
    @GetMapping("/workers")
    public ResponseEntity<ResponseData<Page<MonthlySalaryDto>>> getAllWorkersSalary(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
      return   ResponseData.response(monthlySalaryService.getAllWorkersSalary(pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR','ROLE_HR_MANAGER','ROLE_WORKER','ROLE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<MonthlySalaryDto>> getOne(@PathVariable Long id){

        Optional<MonthlySalaryDto> byId = monthlySalaryService.findById(id);
        if (byId.isEmpty())
            return ResponseData.response("not found", HttpStatus.BAD_REQUEST);

        return ResponseData.response(byId.get());
    }

    @PostMapping
    public ResponseEntity<?> saveMonthlySalary(@RequestBody MonthlySalaryDto monthlySalaryDto){

        Optional<MonthlySalary> byUserAndMonth = monthlySalaryService.findByUserAndMonth(monthlySalaryDto.getUser().getId(), LocalDate.now().getMonth());
        if (byUserAndMonth.isPresent())
            return ResponseData.response("already payed", HttpStatus.BAD_REQUEST);

        Optional<User> userOptional = userService.findById(monthlySalaryDto.getUser().getId());
        if (userOptional.isEmpty())
            return ResponseData.response("user not found", HttpStatus.BAD_REQUEST);

        Optional<Salary> salary = salaryService.findByUserId(monthlySalaryDto.getUser().getId());
        if (salary.isEmpty())
            return ResponseData.response("user's salary doesnt exist", HttpStatus.BAD_REQUEST);

        MonthlySalaryDto result = monthlySalaryService.saveMonthlySalary(userOptional.get(), salary.get(), monthlySalaryDto);

        return ResponseData.response(result);
    }

}
