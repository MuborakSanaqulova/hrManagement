package uz.pdp.hrmanagement.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.hrmanagement.common.ResponseData;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.LoginDto;
import uz.pdp.hrmanagement.payload.UserDto;
import uz.pdp.hrmanagement.payload.UserVerifyDto;

import java.util.Optional;


public interface UserService {

    UserDto addUser(UserDto userDto);

    User addUserEntity(User user);

    boolean existByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);

    void saveWithPassword(User user, UserVerifyDto userVerifyDto);

    Optional<User> findByEmail(String username);

    ResponseEntity<ResponseData<String>> login(LoginDto loginDto);
}
