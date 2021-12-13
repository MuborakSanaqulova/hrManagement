package uz.pdp.hrmanagement.service;

import uz.pdp.hrmanagement.model.User;

import java.time.LocalDate;

public interface TurnpikeService {
    void enter(User user);

    void exit(User user);

    boolean existByUserIdAndDay(Long userId);
}
