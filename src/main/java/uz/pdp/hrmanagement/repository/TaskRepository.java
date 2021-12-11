package uz.pdp.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.hrmanagement.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
