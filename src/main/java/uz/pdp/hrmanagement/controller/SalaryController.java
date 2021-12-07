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
import uz.pdp.hrmanagement.model.Salary;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.SalaryDto;
import uz.pdp.hrmanagement.service.SalaryService;
import uz.pdp.hrmanagement.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/salary")
public class SalaryController {

    @Autowired
    SalaryService salaryService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    @PostMapping
    public ResponseEntity<?> postSalary(@RequestBody SalaryDto salaryDto) {

        Optional<User> byId = userService.findById(salaryDto.getUser().getId());
        if (byId.isEmpty())
            return ResponseData.response("user not found", HttpStatus.BAD_REQUEST);

        boolean userId = salaryService.existByUserId(salaryDto.getUser().getId());

        if (salaryDto.getId() != null || userId)
            return ResponseData.response("already exist", HttpStatus.BAD_REQUEST);

        return ResponseData.response(salaryService.postSalary(salaryDto, byId.get()));
    }

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR','ROLE_HR_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editSalary(@RequestBody Double amount, @PathVariable Long id){
        Optional<Salary> byId = salaryService.findById(id);
        if (byId.isEmpty())
            return ResponseData.response("salary not found", HttpStatus.BAD_REQUEST);

        return ResponseData.response(salaryService.editSalary(amount, byId.get()));
    }

    @PreAuthorize("hasRole('ROLE_HR_MANAGER')")
    @PutMapping("forWorkers/{userId}")
    public ResponseEntity<?> editWorkerSalary(@RequestBody Double amount, @PathVariable Long userId){
        Optional<Salary> worker = salaryService.findWorker(userId);
        if (worker.isEmpty())
            return ResponseData.response("user topilmadi yoki so'ralayotgan user worker rolida emas", HttpStatus.BAD_REQUEST);

        return ResponseData.response(salaryService.editSalary(amount, worker.get()));
    }

    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    @GetMapping
    public ResponseEntity<ResponseData<Page<SalaryDto>>> allSalaries(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseData.response(salaryService.findAll(pageable));
    }

    @PreAuthorize("hasRole('ROLE_HR_MANAGER')")
    @GetMapping
    public ResponseEntity<ResponseData<Page<SalaryDto>>> workersSalaries(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseData.response(salaryService.findWorkers(pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR','ROLE_HR_MANAGER','ROLE_MANAGER','ROLE_WORKER')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<SalaryDto>> getOne(@PathVariable Long id){
       return ResponseData.response(salaryService.findByIdDto(id).get());
    }
}
