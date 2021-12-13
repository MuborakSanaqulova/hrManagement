package uz.pdp.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.hrmanagement.model.Turnpike;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TurnpikeRepository extends JpaRepository<Turnpike, Long> {

    @Query(value = "select * from turnpike where user_id =:userId order by day desc limit 1",nativeQuery = true)
    Optional<Turnpike> findByLastDay(Long userId);

    boolean existsByUserIdAndDay(Long user_id, LocalDate day);

}
