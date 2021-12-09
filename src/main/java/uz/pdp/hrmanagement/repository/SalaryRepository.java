package uz.pdp.hrmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.hrmanagement.model.Salary;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    boolean existsByUserId(Long user_id);

    @Query(value = "select * from salary join users u on u.id = salary.user_id join roles r on r.id = u.role_id where r.id=4 and u.id=:userId", nativeQuery = true)
    Optional<Salary> findWorkerByUserId(Long userId);

    @Query(value = "select * from salary join users u on u.id = salary.user_id join roles r on r.id = u.role_id where r.id=4", nativeQuery = true)
    Page<Salary> findWorkersPage(Pageable pageable);

    Optional<Salary> findByUserId(Long user_id);

}
