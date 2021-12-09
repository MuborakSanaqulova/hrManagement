package uz.pdp.hrmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.hrmanagement.model.MonthlySalary;

import java.time.Month;
import java.util.Optional;

@Repository
public interface MonthlySalaryRepository extends JpaRepository<MonthlySalary, Long> {

    @Query(value = "select * from monthly_salary join users u on u.id = monthly_salary.user_id join roles r on r.id = u.role_id where r.id = 4", nativeQuery = true)
    Page<MonthlySalary> findWorkersMonthlySalaries(Pageable pageable);

    Optional<MonthlySalary> findByUserIdAndMonth(Long user_id, Month month);
}
