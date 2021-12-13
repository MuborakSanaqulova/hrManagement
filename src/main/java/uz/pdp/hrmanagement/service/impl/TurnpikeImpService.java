package uz.pdp.hrmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.Turnpike;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.repository.TurnpikeRepository;
import uz.pdp.hrmanagement.service.TurnpikeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TurnpikeImpService implements TurnpikeService {

    @Autowired
    TurnpikeRepository turnpikeRepository;

    @Override
    public void enter(User user) {
        Turnpike turnpike = new Turnpike();
        turnpike.setEnterTime(LocalDateTime.now());
        turnpike.setDay(LocalDate.now());
        turnpike.setUser(user);
        turnpikeRepository.save(turnpike);
    }

    @Override
    public void exit(User user) {
        Optional<Turnpike> byLastDay = turnpikeRepository.findByLastDay(user.getId());
        Turnpike turnpike = byLastDay.get();
        turnpike.setExitTime(LocalDateTime.now());
        turnpikeRepository.save(turnpike);
    }

    @Override
    public boolean existByUserIdAndDay(Long userId) {
        return turnpikeRepository.existsByUserIdAndDay(userId, LocalDate.now());
    }


}
